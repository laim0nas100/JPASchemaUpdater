//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

public class SchemaUpdaterImpl implements SchemaUpdater {
    private static final Log LOG = LogFactory.getLog(SchemaUpdaterImpl.class);
    private DataSource dataSource;
    private String versionTableName = "SCHEMA_VERSION";
    private final Map<Long, DatabaseVersionUpdater> updatersMap = new HashMap();

    public SchemaUpdaterImpl() {
    }

    @Override
    public boolean update(Long version) {
        Assert.notNull(version, "Parameter version is required");
        LOG.info("Schema update starting for version " + version);
        JdbcTemplate template = new JdbcTemplate(this.getDataSource());
        Long latestVersion = template.queryForObject("SELECT LATEST_VERSION FROM " + this.getVersionTableName(), Long.class);
        if (version < latestVersion) {
            LOG.info("Could not downgrade schema from version " + latestVersion + " to version " + version);
            return false;
        } else {
            Map<Long, DatabaseVersionUpdater> map = this.getUpdatersMap();
            for(long v = latestVersion; v < version; ++v) {
                if (!map.containsKey(v)) {
                    LOG.error("No updater exists for version " + v);
                    return false;
                }
            }

            boolean processed = false;

            for(long v = latestVersion; v < version; ++v) {
                DatabaseVersionUpdater updater = map.get(v);
                DatabasePopulator populator = updater.getDatabasePopulator();
                DatabasePopulatorUtils.execute(populator, this.getDataSource());
                processed = true;
            }

            template.update("UPDATE " + this.getVersionTableName() + " SET LATEST_VERSION=?", new Object[]{version});
            LOG.info("DONE");
            return processed;
        }
    }

    @Override
    public boolean update(final Long version, PlatformTransactionManager transactionManager) {
        Assert.notNull(version, "Parameter version is required");
        Assert.notNull(transactionManager, "Parameter transactionManager is required");
        return new TransactionTemplate(transactionManager).execute((TransactionStatus status) -> SchemaUpdaterImpl.this.update(version));
    }

    @Override
    public void register(DatabaseVersionUpdater updater) {
        Assert.notNull(updater, "Parameter updater is required");
        this.getUpdatersMap().put(updater.getVersionFrom(), updater);
    }

    public void setUpdaters(List<DatabaseVersionUpdater> updaters) {
        Assert.notNull(updaters, "Parameter updaters is required");
        this.getUpdatersMap().clear();
        for (DatabaseVersionUpdater updater : updaters) {
            this.register(updater);
        }

    }

    private String getVersionTableName() {
        return this.versionTableName;
    }

    public void setVersionTableName(String versionTableName) {
        this.versionTableName = versionTableName;
    }

    private Map<Long, DatabaseVersionUpdater> getUpdatersMap() {
        return this.updatersMap;
    }

    private DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
