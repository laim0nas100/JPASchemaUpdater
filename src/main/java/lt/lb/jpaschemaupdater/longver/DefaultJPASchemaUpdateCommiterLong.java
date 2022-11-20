package lt.lb.jpaschemaupdater.longver;

import lt.lb.jpaschemaupdater.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiterLong implements StrictJPASchemaUpdateCommiter {

    protected JPASchemaVersionResolver<Long> JPASchemaResolver;
    
    public final long finalVersion;

    public DefaultJPASchemaUpdateCommiterLong(JPASchemaVersionResolver<Long> JPASchemaResolver, long finalVersion) {
        this.JPASchemaResolver = JPASchemaResolver;
        this.finalVersion = finalVersion;
    }

    @Override
    public JPASchemaVersionResolver<Long> getJPASchemaVersionResolver() {
        return JPASchemaResolver;
    }

    public void setJPASchemaResolver(JPASchemaVersionResolver<Long> JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

    @Override
    public long finalVersion() {
        return finalVersion;
    }

}
