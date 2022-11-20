package lt.lb.jpaschemaupdater;

import lt.lb.jpaschemaupdater.misc.JPASchemaUpdateException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
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

    public boolean hasConnection();

    public boolean hasEntityManager();

    public void beginTransaction();

    public void rollback();

    public void commit();

    @Override
    public void close();

    public static class ConnProvider implements ManagedAccess {

        protected Connection conn;
        protected Supplier<Connection> supplier;
        protected boolean autocommit;

        public ConnProvider(Supplier<Connection> supply, boolean autocommit) throws SQLException {
            this.supplier = supply;
            this.autocommit = autocommit;
        }

        public ConnProvider(Supplier<Connection> supply) throws SQLException {
            this(supply, true);
        }

        @Override
        public Connection getConnection() {
            if (conn == null) {
                conn = supplier.get();
            }
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
            if (conn == null) {
                return;
            }
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

        @Override
        public void commit() {
            if (conn == null) {
                return;
            }
            try {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

        @Override
        public void close() {
            if (conn == null) {
                return;
            }
            try {
                conn.close();
            } catch (Exception ex) {
                throw new ConnEx(ex);
            }
        }

        @Override
        public boolean hasConnection() {
            return true;
        }

        @Override
        public boolean hasEntityManager() {
            return false;
        }

    }

    public static class EmProvider implements ManagedAccess {

        protected Supplier<EntityManager> emSupplier;
        protected EntityManager em;
        protected EntityTransaction transaction;

        public EmProvider(Supplier<EntityManager> emSupply) {
            this.emSupplier = emSupply;
        }

        @Override
        public Connection getConnection() {
            return null;
        }

        @Override
        public EntityManager getEntityManager() {
            if (em == null) {
                em = emSupplier.get();
            }
            return em;
        }

        @Override
        public void beginTransaction() {
            if (transaction == null) {
                transaction = getEntityManager().getTransaction();
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
        }

        @Override
        public void close() {
            if (em == null) {
                return;
            }
            em.close();
            em = null;
            transaction = null;
        }

        @Override
        public boolean hasConnection() {
            return false;
        }

        @Override
        public boolean hasEntityManager() {
            return true;
        }

    }

    public static class CombinedLazyProvider implements ManagedAccess {

        protected List<ManagedAccess> managers;
        protected ManagedAccess active;

        protected List<Consumer<ManagedAccess>> consumeOnActivation = new ArrayList<>();

        public CombinedLazyProvider(ManagedAccess... combined) {
            managers = Arrays.asList(combined);
        }

        @Override
        public Connection getConnection() {
            if (active != null) {
                return active.getConnection();
            }
            if (active == null) {
                ManagedAccess found = managers.stream().filter(m -> m.hasConnection()).findFirst().orElse(null);
                active = found;
                if (active != null) {
                    consumeOnActivation.forEach(c -> c.accept(active));
                    consumeOnActivation.clear();
                }
            }
            if (active == null) {
                return null;
            }
            return active.getConnection();
        }

        @Override
        public EntityManager getEntityManager() {
            if (active != null) {
                return active.getEntityManager();
            }
            if (active == null) {
                ManagedAccess found = managers.stream().filter(m -> m.hasEntityManager()).findFirst().orElse(null);
                active = found;
                if (active != null) {
                    consumeOnActivation.forEach(c -> c.accept(active));
                    consumeOnActivation.clear();
                }
            }

            if (active == null) {
                return null;
            }
            return active.getEntityManager();
        }

        @Override
        public void beginTransaction() {
            consumeOnActivation.add(e -> e.beginTransaction());
        }

        @Override
        public void rollback() {
            if (active != null) {
                active.rollback();
            }
        }

        @Override
        public void commit() {
            if (active != null) {
                active.commit();
            }
        }

        @Override
        public void close() {
            if (active != null) {
                active.close();
            }
            active = null;
            consumeOnActivation.clear();
        }

        @Override
        public boolean hasConnection() {
            return managers.stream().filter(f -> f.hasConnection()).findFirst().isPresent();
        }

        @Override
        public boolean hasEntityManager() {
            return managers.stream().filter(f -> f.hasEntityManager()).findFirst().isPresent();
        }

    }

    public static class CombinedProvider implements ManagedAccess {

        protected List<ManagedAccess> managers = new ArrayList<>();

        public CombinedProvider(ManagedAccess... combined) {
            managers.addAll(Arrays.asList(combined));
        }

        @Override
        public Connection getConnection() {
            return managers.stream().filter(f -> f.hasConnection()).map(m -> m.getConnection()).filter(f -> f != null).findFirst().orElse(null);
        }

        @Override
        public EntityManager getEntityManager() {
            return managers.stream().filter(f -> f.hasEntityManager()).map(m -> m.getEntityManager()).filter(f -> f != null).findFirst().orElse(null);
        }

        @Override
        public void beginTransaction() {
            for (ManagedAccess m : managers) {
                m.beginTransaction();
            }
        }

        @Override
        public void rollback() {
            for (ManagedAccess m : managers) {
                m.rollback();
            }
        }

        @Override
        public void commit() {
            for (ManagedAccess m : managers) {
                m.commit();
            }
        }

        @Override
        public void close() {
            for (ManagedAccess m : managers) {
                m.close();
            }
        }

        @Override
        public boolean hasConnection() {
            return managers.stream().filter(f -> f.hasConnection()).findFirst().isPresent();
        }

        @Override
        public boolean hasEntityManager() {
            return managers.stream().filter(f -> f.hasEntityManager()).findFirst().isPresent();
        }

    }

    /**
     * Other framework is managing trasactions
     */
    public static class UnmanagedEm implements ManagedAccess {

        protected EntityManager entityManager;

        public UnmanagedEm(EntityManager em) {
            this.entityManager = em;
        }

        @Override
        public Connection getConnection() {
            return null;
        }

        @Override
        public EntityManager getEntityManager() {
            return entityManager;
        }

        @Override
        public void beginTransaction() {
        }

        @Override
        public void rollback() {
        }

        @Override
        public void commit() {
        }

        @Override
        public void close() {
        }

        @Override
        public boolean hasConnection() {
            return false;
        }

        @Override
        public boolean hasEntityManager() {
            return true;
        }

    }
}
