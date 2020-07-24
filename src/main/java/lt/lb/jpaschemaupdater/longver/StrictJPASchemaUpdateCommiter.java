package lt.lb.jpaschemaupdater.longver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lt.lb.jpaschemaupdater.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.JPASchemaVersionResolver;
import lt.lb.jpaschemaupdater.misc.JPASchemaUpdateException;
import org.apache.commons.logging.Log;

/**
 *
 * @author laim0nas100
 */
public interface StrictJPASchemaUpdateCommiter extends JPASchemaUpdateCommiter<Long> {

    /**
     * Get version to update schema to (doesn't go above) lower that 0 means
     * ignore this parameter;
     *
     * @return
     */
    public long finalVersion();

    public default boolean needsUpdate() {
        long currentVer = getJPASchemaVersionResolver().getCurrentVersion();
        long finalVersion = finalVersion();
        if (finalVersion > 0 && this.compareVersions(finalVersion, currentVer) <= 0) {
            return false;
        }
        return true;
    }

    @Override
    public default void updateSchema(List<JPASchemaUpdateInstance<Long>> updates) throws Exception {

        JPASchemaVersionResolver<Long> resolver = getJPASchemaVersionResolver();
        long currentVer = resolver.getCurrentVersion();
        long finalVersion = finalVersion();
        Log log = getLog();
        if (!needsUpdate()) {
            if (log.isInfoEnabled()) {
                log.info("No updates needed, current version:" + currentVer + " final version:" + finalVersion);
            }

            return;
        }
        
        Set<Long> versions = new HashSet<>();
        for(JPASchemaUpdateInstance<Long> update : updates){
            Long ver = update.getVersion();
            if(versions.contains(ver)){
                throw new JPASchemaUpdateException("Terminating because multiple instances with the same version found: "+ver);
            }
            versions.add(ver);
        }
        sortUpdates(updates);
        for (JPASchemaUpdateInstance<Long> update : updates) {
            long newVer = update.getVersion();
            if (finalVersion > 0 && finalVersion < newVer) {
                if (log.isInfoEnabled()) {
                    log.info("No more updates needed, current version:" + currentVer + " final version:" + finalVersion);
                }

                return;
            }
            if (newVer > currentVer) {
                if (newVer - currentVer != 1) { // increment size must be 1
                    throw new JPASchemaUpdateException("Terminating to prevent version skip " + currentVer + " -> " + newVer);
                }
                doUpdate(update);
                resolver.setCurrentVersion(newVer);
                currentVer = newVer;
            }
        }
    }

}
