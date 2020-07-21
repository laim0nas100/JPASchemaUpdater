package lt.lb.jpaschemaupdater.ported.specific;

import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ported.JPASchemaVersionResolver;
import lt.lb.jpaschemaupdater.ported.ManagedAccess;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiter implements JPASchemaUpdateCommiter {

    protected static Log log = LogFactory.getLog(DefaultJPASchemaUpdateCommiter.class);
    protected JPASchemaVersionResolver JPASchemaResolver;

    public DefaultJPASchemaUpdateCommiter(JPASchemaVersionResolver JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

    public DefaultJPASchemaUpdateCommiter() {
    }

    @Override
    public JPASchemaVersionResolver getJPASchemaResolver() {
        return JPASchemaResolver;
    }

    public void setJPASchemaResolver(JPASchemaVersionResolver JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

    @Override
    public void inTransaction(JPASchemaUpdateInstance instance, ManagedAccess ma) {

        List<JPASchemaUpdateStategy> updates = instance.getUpdates();
        Long version = instance.getVersion();
        log.info("Starting schema update version " + version);
        int i = 1;
        int size = updates.size();
        for (JPASchemaUpdateStategy strategy : updates) {
            log.info("using strategy nr. " + i + " of " + size);
            strategy.doUpdate(ma);
            i++;
        }
        log.info("Done with schema update version " + version);
    }

}
