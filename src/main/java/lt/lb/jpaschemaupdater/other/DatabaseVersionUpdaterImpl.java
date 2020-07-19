//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.util.Assert;

public class DatabaseVersionUpdaterImpl implements DatabaseVersionUpdater, InitializingBean {
    private Long versionFrom;
    private DatabasePopulator databasePopulator;

    public DatabaseVersionUpdaterImpl() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.getVersionFrom());
        Assert.notNull(this.getDatabasePopulator());
    }

    @Override
    public Long getVersionFrom() {
        return this.versionFrom;
    }

    public void setVersionFrom(Long versionFrom) {
        this.versionFrom = versionFrom;
    }

    public DatabaseVersionUpdaterImpl withVersionFrom(Long versionFrom) {
        this.setVersionFrom(versionFrom);
        return this;
    }

    @Override
    public DatabasePopulator getDatabasePopulator() {
        return this.databasePopulator;
    }

    public void setDatabasePopulator(DatabasePopulator databasePopulator) {
        this.databasePopulator = databasePopulator;
    }

    public DatabaseVersionUpdaterImpl withDatabasePopulator(DatabasePopulator databasePopulator) {
        this.setDatabasePopulator(databasePopulator);
        return this;
    }
}
