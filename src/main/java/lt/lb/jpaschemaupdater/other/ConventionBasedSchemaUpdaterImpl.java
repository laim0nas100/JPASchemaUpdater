//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.OrderComparator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

public class ConventionBasedSchemaUpdaterImpl implements InitializingBean {
    private static final Log LOG = LogFactory.getLog(ConventionBasedSchemaUpdaterImpl.class);
    private VersionResolver versionResolver = new VersionResolverImpl();
    private List<PopulatorConfigurer> populatorConfigurers = Lists.newArrayList();
    private Long version;
    private boolean update;
    private String pattern;
    private boolean transactional;
    private boolean applyDefaults = true;
    private SchemaUpdater schemaUpdater;
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private List<DatabaseVersionUpdater> extraUpdaters = Lists.newArrayList();
    private PlatformTransactionManager transactionManager;

    public ConventionBasedSchemaUpdaterImpl() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.applyDefaults) {
            this.getPopulatorConfigurers().add(new DefaultScriptConfigurerImpl());
            this.getPopulatorConfigurers().add(new DefaultSeparatorConfigurerImpl());
            this.getPopulatorConfigurers().add(new DefaultSqlScriptEncodingConfigurerImpl());
        }

        Collections.sort(this.getPopulatorConfigurers(), OrderComparator.INSTANCE);

        try {
            Resource[] resources = this.getResourcePatternResolver().getResources(this.getPattern());
            Resource[] arr$ = resources;
            int len$ = resources.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Resource resource = arr$[i$];
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                this.configurePopulator(resource, populator);
                Long ver = getVersionResolver().getVersion(resource)- 1L;
                DatabaseVersionUpdaterImpl updater = new DatabaseVersionUpdaterImpl()
                        .withVersionFrom(ver)
                        .withDatabasePopulator(populator);
                this.getSchemaUpdater().register(updater);
            }
        } catch (IOException var7) {
            LOG.error(var7.getMessage(), var7);
            throw new IllegalStateException(var7);
        }

        for (DatabaseVersionUpdater updater : this.getExtraUpdaters()) {
            this.getSchemaUpdater().register(updater);
        }

        if (this.isUpdate()) {
            if (this.isTransactional()) {
                this.getSchemaUpdater().update(this.getVersion(), this.getTransactionManager());
            } else {
                this.getSchemaUpdater().update(this.getVersion());
            }
        }

    }

    private void configurePopulator(Resource resource, ResourceDatabasePopulator populator) {
        for (PopulatorConfigurer configurer : this.getPopulatorConfigurers()) {
            configurer.configure(populator, resource);
        }

    }

    private Long getVersion() {
        return this.version;
    }

    private boolean isUpdate() {
        return this.update;
    }

    private String getPattern() {
        return this.pattern;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    private SchemaUpdater getSchemaUpdater() {
        return this.schemaUpdater;
    }

    public void setSchemaUpdater(SchemaUpdater schemaUpdater) {
        this.schemaUpdater = schemaUpdater;
    }

    private ResourcePatternResolver getResourcePatternResolver() {
        return this.resourcePatternResolver;
    }

    private PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private boolean isTransactional() {
        return this.transactional;
    }

    public void setTransactional(boolean transactional) {
        this.transactional = transactional;
    }

    private VersionResolver getVersionResolver() {
        return this.versionResolver;
    }

    public void setVersionResolver(VersionResolver versionResolver) {
        this.versionResolver = versionResolver;
    }

    public List<PopulatorConfigurer> getPopulatorConfigurers() {
        return this.populatorConfigurers;
    }

    public void setPopulatorConfigurers(List<PopulatorConfigurer> populatorConfigurers) {
        this.populatorConfigurers = populatorConfigurers;
    }

    public boolean isApplyDefaults() {
        return this.applyDefaults;
    }

    public void setApplyDefaults(boolean applyDefaults) {
        this.applyDefaults = applyDefaults;
    }

    public List<DatabaseVersionUpdater> getExtraUpdaters() {
        return this.extraUpdaters;
    }

    public void setExtraUpdaters(List<DatabaseVersionUpdater> extraUpdaters) {
        this.extraUpdaters = extraUpdaters;
    }
}
