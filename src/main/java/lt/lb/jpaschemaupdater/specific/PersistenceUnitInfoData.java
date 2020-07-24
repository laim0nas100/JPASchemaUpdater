package lt.lb.jpaschemaupdater.specific;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Supplier;
import javax.sql.DataSource;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;

public class PersistenceUnitInfoData implements PersistenceUnitInfo {

    public String persistenceXMLSchemaVersion = "2.1";
    public String persistenceProviderClassName = "org.hibernate.jpa.HibernatePersistenceProvider";
    public String persistenceUnitName;
    public PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    public List<String> managedClassNames = new ArrayList<>();
    public List<String> mappingFileNames = new ArrayList<>();
    public List<URL> jarFileUrls = new ArrayList<>();
    public Properties properties;
    public DataSource jtaDataSource;
    public DataSource nonjtaDataSource;
    public URL persistenceUnitRootUrl;
    public List<ClassTransformer> transformers = new ArrayList<>();
    public boolean excludeUnlistedClasses = false;
    public SharedCacheMode sharedCacheMode = SharedCacheMode.UNSPECIFIED;
    public ValidationMode validationMode = ValidationMode.AUTO;
    public Supplier<ClassLoader> classloader = () -> Thread.currentThread().getContextClassLoader();
    public Supplier<ClassLoader> newTempClassLoader = () -> null;

    @Override
    public String getPersistenceUnitName() {
        return persistenceUnitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return persistenceProviderClassName;
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return transactionType;
    }

    public void setJtaDataSource(DataSource jtaDataSource) {
        this.jtaDataSource = jtaDataSource;
        this.nonjtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.JTA;
    }

    @Override
    public DataSource getJtaDataSource() {
        return jtaDataSource;
    }

    public void setNonJtaDataSource(DataSource nonJtaDataSource) {
        this.nonjtaDataSource = nonJtaDataSource;
        this.jtaDataSource = null;
        transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return nonjtaDataSource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return mappingFileNames;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return jarFileUrls;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return persistenceUnitRootUrl;
    }

    @Override
    public List<String> getManagedClassNames() {
        return managedClassNames;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return excludeUnlistedClasses;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return sharedCacheMode;
    }

    @Override
    public ValidationMode getValidationMode() {
        return validationMode;
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return persistenceXMLSchemaVersion;
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.classloader.get();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
        transformers.add(transformer);
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return this.newTempClassLoader.get();
    }

}
