package lt.lb.jpaschemaupdater;

import java.sql.Connection;
import java.util.function.Consumer;
import javax.persistence.EntityManager;

/**
 *
 * Singular update unit.
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdateStategy {

    /**
     * Assume we are in transaction
     *
     * @param conn
     */
    public void doUpdate(ManagedAccess conn);

    /**
     * Update strategy with provided entity manager
     */
    public static interface EMSchemaUpdateStrategy extends JPASchemaUpdateStategy, Consumer<EntityManager> {

        @Override
        public default void doUpdate(ManagedAccess conn) {
            accept(conn.getEntityManager());
        }

    }

    /**
     * Update strategy with provided entity manager
     */
    public static interface ConnectionSchemaUpdateStrategy extends JPASchemaUpdateStategy, Consumer<Connection> {

        @Override
        public default void doUpdate(ManagedAccess conn) {
            accept(conn.getConnection());
        }

    }
}
