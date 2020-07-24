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
public class BaseSchemaUpdateInstance<Ver> implements JPASchemaUpdateInstance<Ver> {

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

    public BaseSchemaUpdateInstance<Ver> addStrategy(JPASchemaUpdateStategy strategy) {
        if (updates == null) {
            updates = new ArrayList<>();
        }
        updates.add(strategy);
        return this;
    }

    public BaseSchemaUpdateInstance<Ver> addConnectionStrategy(ConnectionSchemaUpdateStrategy strategy) {
        return addStrategy(strategy);
    }

    public BaseSchemaUpdateInstance<Ver> addEMStrategy(EMSchemaUpdateStrategy strategy) {
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
