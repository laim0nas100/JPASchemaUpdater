package lt.lb.jpaschemaupdater.ported.specific;

import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdateCommiter implements JPASchemaUpdateCommiter{

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
    
}
