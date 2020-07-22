package lt.lb.jpaschemaupdater.ported.specific;

import lt.lb.jpaschemaupdater.ported.StrictJPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiter implements StrictJPASchemaUpdateCommiter {

    protected JPASchemaVersionResolver JPASchemaResolver;
    protected long finalVersion;

    public DefaultJPASchemaUpdateCommiter(JPASchemaVersionResolver JPASchemaResolver, long finalVersion) {
        this.JPASchemaResolver = JPASchemaResolver;
        this.finalVersion = finalVersion;
    }

    @Override
    public long finalVersion() {
        return finalVersion;
    }

    @Override
    public JPASchemaVersionResolver getJPASchemaResolver() {
        return JPASchemaResolver;
    }

    public void setJPASchemaResolver(JPASchemaVersionResolver JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

}
