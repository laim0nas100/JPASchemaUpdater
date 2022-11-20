package lt.lb.jpaschemaupdater.specific;

import java.util.ArrayList;
import java.util.List;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy.EMSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public abstract class BaseSchemaUpdateInstance<Ver, M extends BaseSchemaUpdateInstance<Ver,M>> implements JPASchemaUpdateInstance<Ver> {

    protected Ver version;
    protected ManagedAccessFactory managedAccessFactory;
    protected List<JPASchemaUpdateStategy> updates;

    public BaseSchemaUpdateInstance() {

    }

    public BaseSchemaUpdateInstance(Ver version, ManagedAccessFactory managedAccesFactory) {
        this.version = version;
        this.managedAccessFactory = managedAccesFactory;
    }

    @Override
    public Ver getVersion() {
        return version;
    }

    public void setVersion(Ver version) {
        this.version = version;
    }
    
    protected abstract M me();

    public M addStrategy(JPASchemaUpdateStategy strategy) {
        M me = me();
        if (me.updates == null) {
            me.updates = new ArrayList<>();
        }
        me.updates.add(strategy);
        return me;
    }

    public M addConnectionStrategy(ConnectionSchemaUpdateStrategy strategy) {
        return addStrategy(strategy);
    }

    public M addEMStrategy(EMSchemaUpdateStrategy strategy) {
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
