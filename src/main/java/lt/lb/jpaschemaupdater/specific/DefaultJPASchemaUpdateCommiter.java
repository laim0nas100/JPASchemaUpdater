package lt.lb.jpaschemaupdater.specific;

import lt.lb.jpaschemaupdater.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiter<Ver> implements JPASchemaUpdateCommiter<Ver> {

    protected JPASchemaVersionResolver<Ver> JPASchemaResolver;

    public DefaultJPASchemaUpdateCommiter(JPASchemaVersionResolver JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

    @Override
    public JPASchemaVersionResolver getJPASchemaVersionResolver() {
        return JPASchemaResolver;
    }

    public void setJPASchemaResolver(JPASchemaVersionResolver JPASchemaResolver) {
        this.JPASchemaResolver = JPASchemaResolver;
    }

}
