package lt.lb.jpaschemaupdater.ported.specific;

import lt.lb.jpaschemaupdater.ported.JPASchemaInstanceMaker;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdater;

/**
 *
 * @author Laimonas-Beniusis-PC
 */
public class DefaultJPASchemaUpdater implements JPASchemaUpdater{

    protected JPASchemaInstanceMaker instanceMaker;
    protected JPASchemaUpdateCommiter updateCommiter;

    
    @Override
    public JPASchemaInstanceMaker getInstanceMaker() {
        return instanceMaker;
    }

    public void setInstanceMaker(JPASchemaInstanceMaker instanceMaker) {
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
