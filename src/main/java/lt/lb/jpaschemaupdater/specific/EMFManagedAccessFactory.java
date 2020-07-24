package lt.lb.jpaschemaupdater.specific;

import javax.persistence.EntityManagerFactory;
import lt.lb.jpaschemaupdater.ManagedAccess;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class EMFManagedAccessFactory implements ManagedAccessFactory {

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
