package lt.lb.jpaschemaupdater.longver;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiterLong<Ver> implements StrictJPASchemaUpdateCommiter {

    protected JPASchemaVersionResolverLong JPASchemaResolver;
    
    public final long finalVersion;

    public DefaultJPASchemaUpdateCommiterLong(JPASchemaVersionResolverLong JPASchemaResolver, long finalVersion) {
        this.JPASchemaResolver = JPASchemaResolver;
        this.finalVersion = finalVersion;
    }

    @Override
    public JPASchemaVersionResolverLong getJPASchemaVersionResolver() {
        return JPASchemaResolver;
    }

    public void setJPASchemaResolver(JPASchemaVersionResolverLong JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

    @Override
    public long finalVersion() {
        return finalVersion;
    }

}
