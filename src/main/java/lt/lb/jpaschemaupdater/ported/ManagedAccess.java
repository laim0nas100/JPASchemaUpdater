package lt.lb.jpaschemaupdater.ported;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author laim0nas100
 */
public interface ManagedAccess extends AutoCloseable {

    public static class ConnEx extends JPASchemaUpdateException {

        public ConnEx(Throwable cause) {
            super(cause);
        }

        public ConnEx(String message) {
            super(message);
        }

    }

    public Connection getConnection();

    public EntityManager getEntityManager();

    public void beginTransaction();

    public void rollback();

    public void commit();

    @Override
    public void close();

    public static class ConnProvider implements ManagedAccess {

        protected Connection conn;

        public ConnProvider(Connection conn, boolean autocommit) throws SQLException {
            this.conn = conn;
            this.conn.setAutoCommit(autocommit);
        }

        public ConnProvider(Connection conn) throws SQLException {
            this(conn, true);
        }

        @Override
        public Connection getConnection() {
            return conn;
        }

        @Override
        public EntityManager getEntityManager() {
            return null;
        }

        @Override
        public void beginTransaction() {
        }

        @Override
        public void rollback() {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

        @Override
        public void commit() {
            try {
                conn.commit();
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

        @Override
        public void close() {
            try {
                conn.close();
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

    }

    public static class EmProvider implements ManagedAccess {

        protected EntityManager em;
        protected EntityTransaction transaction;

        public EmProvider(EntityManager em) {
            this.em = em;
        }

        @Override
        public Connection getConnection() {
            return null;
        }

        @Override
        public EntityManager getEntityManager() {
            return em;
        }

        @Override
        public void beginTransaction() {
            if (transaction == null) {
                transaction = em.getTransaction();
            }
            if (!transaction.isActive()) {
                transaction.begin();
            }

        }

        @Override
        public void rollback() {
            if (transaction == null) {
                throw new ConnEx("No transaction was made");
            }
            if (!transaction.isActive()) {
                throw new ConnEx("Transaction is not active");
            }
            transaction.rollback();
            transaction = null;
        }

        @Override
        public void commit() {
            if (transaction == null) {
                throw new ConnEx("No transaction was made");
            }
            if (!transaction.isActive()) {
                throw new ConnEx("Transaction is not active");
            }
            transaction.commit();
            transaction = null;
            em.flush();

        }

        @Override
        public void close() {
            em.close();
        }

    }

    public static class CombinedProvider implements ManagedAccess {

        protected List<ManagedAccess> managers = new ArrayList<>();

        public CombinedProvider(Collection<ManagedAccess> combined) {
            managers.addAll(combined);
        }

        @Override
        public Connection getConnection() {
            return managers.stream().map(m -> m.getConnection()).filter(f -> f != null).findFirst().orElse(null);
        }

        @Override
        public EntityManager getEntityManager() {
            return managers.stream().map(m -> m.getEntityManager()).filter(f -> f != null).findFirst().orElse(null);
        }

        @Override
        public void beginTransaction() {
            for(ManagedAccess m:managers){
                m.beginTransaction();
            }
        }

        @Override
        public void rollback() {
            for(ManagedAccess m:managers){
                m.rollback();
            }
        }

        @Override
        public void commit() {
            for(ManagedAccess m:managers){
                m.commit();
            }
        }

        @Override
        public void close() {
            for(ManagedAccess m:managers){
                m.close();
            }
        }

    }
}
