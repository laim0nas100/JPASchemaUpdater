package lt.lb.jpaschemaupdater.specific;

import lt.lb.jpaschemaupdater.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstanceMaker;
import lt.lb.jpaschemaupdater.JPASchemaUpdater;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdater<Ver> implements JPASchemaUpdater<Ver> {

    protected JPASchemaUpdateInstanceMaker<Ver> instanceMaker;
    protected JPASchemaUpdateCommiter<Ver> updateCommiter;

    public DefaultJPASchemaUpdater() {
    }

    public DefaultJPASchemaUpdater(JPASchemaUpdateInstanceMaker<Ver> instanceMaker, JPASchemaUpdateCommiter<Ver> updateCommiter) {
        this.instanceMaker = instanceMaker;
        this.updateCommiter = updateCommiter;
    }

    @Override
    public JPASchemaUpdateInstanceMaker<Ver> getInstanceMaker() {
        return instanceMaker;
    }

    public void setInstanceMaker(JPASchemaUpdateInstanceMaker<Ver> instanceMaker) {
        this.instanceMaker = instanceMaker;
    }

    @Override
    public JPASchemaUpdateCommiter<Ver> getUpdateCommiter() {
        return updateCommiter;
    }

    public void setUpdateCommiter(JPASchemaUpdateCommiter<Ver> updateCommiter) {
        this.updateCommiter = updateCommiter;
    }

}
