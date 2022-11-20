package lt.lb.jpaschemaupdater;

import lt.lb.jpaschemaupdater.misc.JPASchemaUpdateException;
import java.util.Collections;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author laim0nas100
 * @param <Ver>
 */
public interface JPASchemaUpdateCommiter<Ver> {

    public default Log getLog() {
        return LogFactory.getLog(this.getClass());
    }

    public JPASchemaVersionResolver<Ver> getJPASchemaVersionResolver();

    /**
     * Establish transactions and do the updates
     *
     * @param instance
     * @throws java.lang.Exception
     */
    public default void doUpdate(JPASchemaUpdateInstance<Ver> instance) throws Exception {
        ManagedAccessFactory managedAccessFactory = instance.getManagedAccessFactory();
        try (ManagedAccess ma = managedAccessFactory.create()) { // autoclose
            try {
                ma.beginTransaction();
                inTransaction(instance, ma);
                ma.commit();
            } catch (Exception ex) {
                try {
                    ma.rollback();
                } catch (Exception rollEx) {
                    getLog().error("Error during rollback", rollEx);
                }

                throw new JPASchemaUpdateException(ex);
            }

        }
    }

    public default void inTransaction(JPASchemaUpdateInstance<Ver> instance, ManagedAccess ma) {

        List<JPASchemaUpdateStategy> updates = instance.getUpdates();
        Ver version = instance.getVersion();
        Log logger = getLog();
        if (logger.isInfoEnabled()) {
            logger.info("Starting schema update version " + version);
        }

        int i = 1;
        int size = updates.size();
        for (JPASchemaUpdateStategy strategy : updates) {
            if (logger.isInfoEnabled()) {
                logger.info("Using strategy nr. " + i + " of " + size);
            }

            long startTime = System.currentTimeMillis();
            strategy.doUpdate(ma);
            long elapsedTime = System.currentTimeMillis() - startTime;
            if (logger.isInfoEnabled()) {
                logger.info("Executed update strategy in " + elapsedTime + " ms.");
            }
            i++;
        }
        if (logger.isInfoEnabled()) {
            logger.info("Done with schema update version " + version);
        }

    }

    public default void updateSchema(List<JPASchemaUpdateInstance<Ver>> updates) throws Exception {
        JPASchemaVersionResolver<Ver> resolver = getJPASchemaVersionResolver();
        sortUpdates(updates);
        Ver currentVer = resolver.getCurrentVersion();
        for (JPASchemaUpdateInstance<Ver> update : updates) {
            Ver newVer = update.getVersion();
            if(compareVersions(currentVer, newVer) <= 0){
                doUpdate(update);
                resolver.setCurrentVersion(newVer);
                currentVer = newVer;
            }
        }
    }
    
    public default int compareVersions(Ver o1, Ver o2){
        return getJPASchemaVersionResolver().getVersionComparator().compare(o1, o2);
    }
    
    public default int compareInstances(JPASchemaUpdateInstance<Ver> ins1, JPASchemaUpdateInstance<Ver> ins2){
        return compareVersions(ins1.getVersion(), ins2.getVersion());
    }
    
    public default void sortUpdates(List<JPASchemaUpdateInstance<Ver>> updates){
        Collections.sort(updates, this::compareInstances);
    }

}
