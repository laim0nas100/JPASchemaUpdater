package lt.lb.jpaschemaupdater.ported;

import lt.lb.jpaschemaupdater.ported.misc.JPASchemaUpdateException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdateCommiter {

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

                }

                throw new JPASchemaUpdateException(ex);
            }

        }
    }

    public default void inTransaction(JPASchemaUpdateInstance instance, ManagedAccess ma) {
        for (JPASchemaUpdateStategy strategy : instance.getUpdates()) {
            strategy.doUpdate(ma);
        }
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
