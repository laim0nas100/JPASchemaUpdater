package lt.lb.jpaschemaupdater.longver;

import lt.lb.jpaschemaupdater.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstanceMaker;
import lt.lb.jpaschemaupdater.specific.DefaultJPASchemaUpdater;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdaterLong extends DefaultJPASchemaUpdater<Long>{

    public DefaultJPASchemaUpdaterLong() {
    }

    public DefaultJPASchemaUpdaterLong(JPASchemaUpdateInstanceMaker<Long> instanceMaker, JPASchemaUpdateCommiter<Long> updateCommiter) {
        super(instanceMaker, updateCommiter);
    }
    
}
