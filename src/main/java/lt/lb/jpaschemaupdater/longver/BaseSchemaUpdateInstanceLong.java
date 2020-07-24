package lt.lb.jpaschemaupdater.longver;

import lt.lb.jpaschemaupdater.specific.BaseSchemaUpdateInstance;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class BaseSchemaUpdateInstanceLong extends BaseSchemaUpdateInstance<Long> implements JPASchemaUpdateInstanceLong {


    public BaseSchemaUpdateInstanceLong() {

    }

    public BaseSchemaUpdateInstanceLong(Long version, ManagedAccessFactory managedAccessFactory) {
        super(version,managedAccessFactory);
    }

    @Override
    public BaseSchemaUpdateInstanceLong addEMStrategy(JPASchemaUpdateStategy.EMSchemaUpdateStrategy strategy) {
        return (BaseSchemaUpdateInstanceLong) super.addEMStrategy(strategy);
    }

    @Override
    public BaseSchemaUpdateInstanceLong addConnectionStrategy(JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy strategy) {
        return (BaseSchemaUpdateInstanceLong) super.addConnectionStrategy(strategy);
    }

    @Override
    public BaseSchemaUpdateInstanceLong addStrategy(JPASchemaUpdateStategy strategy) {
        return (BaseSchemaUpdateInstanceLong) super.addStrategy(strategy);
    }
    
    

}
