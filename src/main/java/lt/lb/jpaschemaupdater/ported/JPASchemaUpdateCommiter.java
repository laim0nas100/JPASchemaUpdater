package lt.lb.jpaschemaupdater.ported;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Laimonas-Beniusis-PC
 */
public interface JPASchemaUpdateCommiter {


    public JPASchemaVersionResolver getJPASchemaResolver();
    
    /**
     * Establish transactions and do the updates
     *
     * @param instance
     */
    public default void doUpdate(JPASchemaUpdateInstance instance) {

        ManagedAccess ma = instance.createManagedAccess();
        
        ma.beginTransaction();
        for(JPASchemaUpdateStategy strategy:instance.getUpdates()){
            strategy.doUpdate(ma);
        }
        ma.commit();
        ma.close();
    }

    public default void updateSchema(List<JPASchemaUpdateInstance> updates) {
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
