package lt.lb.jpaschemaupdater.ported.specific;

import java.util.ArrayList;
import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy.EMSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ported.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class BaseSchemaUpdateInstance<T extends BaseSchemaUpdateInstance> implements JPASchemaUpdateInstance {

    protected Long version;
    protected ManagedAccessFactory managedAccessFactory;
    protected List<JPASchemaUpdateStategy> updates = new ArrayList<>();

    public BaseSchemaUpdateInstance() {

    }

    public BaseSchemaUpdateInstance(Long version, ManagedAccessFactory managedAccesFactory) {
        this.version = version;
        this.managedAccessFactory = managedAccesFactory;
    }

    @Override
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public T addStrategy(JPASchemaUpdateStategy strategy) {
        updates.add(strategy);
        return (T) this;
    }
    
    public T addConnectionStrategy(ConnectionSchemaUpdateStrategy strategy){
        return addStrategy(strategy);
    }
    
    public T addEMStrategy(EMSchemaUpdateStrategy strategy){
        return addStrategy(strategy);
    }

    @Override
    public ManagedAccessFactory getManagedAccessFactory() {
        return managedAccessFactory;
    }

    @Override
    public List<JPASchemaUpdateStategy> getUpdates() {
        return updates;
    }


    public void setUpdates(List<JPASchemaUpdateStategy> updates) {
        this.updates = updates;
    }

    public void setManagedAccessFactory(ManagedAccessFactory managedAccessFactory) {
        this.managedAccessFactory = managedAccessFactory;
    }
    
    

}
