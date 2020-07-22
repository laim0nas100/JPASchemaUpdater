package lt.lb.jpaschemaupdater.ported.specific;

import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdater;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstanceMaker;

/**
 *
 * @author laim0nas100
 */
public class DefaultJPASchemaUpdater implements JPASchemaUpdater {

    protected JPASchemaUpdateInstanceMaker instanceMaker;
    protected JPASchemaUpdateCommiter updateCommiter;

    public DefaultJPASchemaUpdater() {
    }

    public DefaultJPASchemaUpdater(JPASchemaUpdateInstanceMaker instanceMaker, JPASchemaUpdateCommiter updateCommiter) {
        this.instanceMaker = instanceMaker;
        this.updateCommiter = updateCommiter;
    }

    @Override
    public JPASchemaUpdateInstanceMaker getInstanceMaker() {
        return instanceMaker;
    }

    public void setInstanceMaker(JPASchemaUpdateInstanceMaker instanceMaker) {
        this.instanceMaker = instanceMaker;
    }

    @Override
    public JPASchemaUpdateCommiter getUpdateCommiter() {
        return updateCommiter;
    }

    public void setUpdateCommiter(JPASchemaUpdateCommiter updateCommiter) {
        this.updateCommiter = updateCommiter;
    }

}
