package lt.lb.jpaschemaupdater.specific;

import java.util.Arrays;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lt.lb.jpaschemaupdater.ManagedAccess;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class EMFConnectionFactory implements ManagedAccessFactory {

    private EntityManagerFactory entityManagerFactory;
    private DataSource dataSource;

    public EMFConnectionFactory(EntityManagerFactory entityManagerFactory, DataSource dataSource) {
        this.entityManagerFactory = entityManagerFactory;
        this.dataSource = dataSource;
    }

    public EMFConnectionFactory() {
    }

    @Override
    public ManagedAccess create() throws Exception {

        ManagedAccess.ConnProvider connProvider = new ManagedAccess.ConnProvider(dataSource.getConnection());
        ManagedAccess.EmProvider emProvider = new ManagedAccess.EmProvider(entityManagerFactory.createEntityManager());

        return new ManagedAccess.CombinedProvider(Arrays.asList(connProvider, emProvider));
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
