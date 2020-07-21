package lt.lb.jpaschemaupdater.ported.specific;

import javax.persistence.EntityManagerFactory;
import lt.lb.jpaschemaupdater.ported.ManagedAccess;
import lt.lb.jpaschemaupdater.ported.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class EMFManagedAccessFactory implements ManagedAccessFactory{

    private EntityManagerFactory entityManagerFactory;

    public EMFManagedAccessFactory() {
    }

    public EMFManagedAccessFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    @Override
    public ManagedAccess create() throws Exception {
        return new ManagedAccess.EmProvider(entityManagerFactory.createEntityManager());
    }
    
}
