package lt.lb.jpaschemaupdater.ported;

import lt.lb.jpaschemaupdater.ported.misc.JPASchemaUpdateException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdateCommiter {
    
    public default Log getLog(){
        return LogFactory.getLog(this.getClass());
    }

    public JPASchemaVersionResolver getJPASchemaResolver();

    /**
     * Establish transactions and do the updates
     *
     * @param instance
     * @throws java.lang.Exception
     */
    public default void doUpdate(JPASchemaUpdateInstance instance) throws Exception {
        ManagedAccessFactory managedAccessFactory = instance.getManagedAccessFactory();
        try (ManagedAccess ma = managedAccessFactory.create()) {
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

    
    
    public default void inTransaction(JPASchemaUpdateInstance instance, ManagedAccess ma) {

        List<JPASchemaUpdateStategy> updates = instance.getUpdates();
        Long version = instance.getVersion();
        getLog().info("Starting schema update version " + version);
        int i = 1;
        int size = updates.size();
        for (JPASchemaUpdateStategy strategy : updates) {
            getLog().info("Using strategy nr. " + i + " of " + size);
            strategy.doUpdate(ma);
            i++;
        }
        getLog().info("Done with schema update version " + version);
    }


    public default void updateSchema(List<JPASchemaUpdateInstance> updates) throws Exception {
        Collections.sort(updates, Comparator.comparing(t -> t.getVersion()));
        JPASchemaVersionResolver resolver = getJPASchemaResolver();
        long currentVer = resolver.getCurrentVersion();
        for (JPASchemaUpdateInstance update : updates) {
            long newVer = update.getVersion();
            if (newVer > currentVer) {
                doUpdate(update);
                resolver.setCurrentVersion(newVer);
                currentVer = newVer;
            }
        }
    }

}
