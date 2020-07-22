package lt.lb.jpaschemaupdater.ported;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.JPASchemaVersionResolver;
import lt.lb.jpaschemaupdater.ported.misc.JPASchemaUpdateException;

/**
 *
 * @author laim0nas100
 */
public interface StrictJPASchemaUpdateCommiter extends JPASchemaUpdateCommiter {

    /**
     * Get version to update schema to (doesn't go above) lower that 0 means
     * ignore this parameter;
     *
     * @return
     */
    public default long finalVersion() {
        return -1;
    }
    
    public default boolean needsUpdate(){
        long currentVer = getJPASchemaResolver().getCurrentVersion();
        long finalVersion = finalVersion();
        if (finalVersion > 0 && finalVersion <= currentVer) {
            return false;
        }
        return true;
    }

    @Override
    public default void updateSchema(List<JPASchemaUpdateInstance> updates) throws Exception {

        JPASchemaVersionResolver resolver = getJPASchemaResolver();
        long currentVer = resolver.getCurrentVersion();
        long finalVersion = finalVersion();
        if (!needsUpdate()){
            getLog().info("No updates needed, current version:" + currentVer + " final version:" + finalVersion);
            return;
        }
        Collections.sort(updates, Comparator.comparing(t -> t.getVersion()));
        for (JPASchemaUpdateInstance update : updates) {
            long newVer = update.getVersion();
            if (finalVersion > 0 && finalVersion < newVer) {
                getLog().info("No more updates needed, current version:" + currentVer + " final version:" + finalVersion);
                return;
            }
            if (newVer > currentVer) {
                if (newVer - currentVer != 1) { // increment size must be 1
                    throw new JPASchemaUpdateException("Preventing version skip, because trying to: " + currentVer + " -> " + newVer);
                }
                doUpdate(update);
                resolver.setCurrentVersion(newVer);
                currentVer = newVer;
            }
        }
    }

}
