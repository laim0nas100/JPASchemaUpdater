package lt.lb.jpaschemaupdater;

import java.util.List;

/**
 *
 * Collects JPASchemaUpdateStategy and presents as a update batch with given
 * version. Also specifies how to connect to the database via ManagedAccess.
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdateInstance<Ver> {

    public Ver getVersion();

    public ManagedAccessFactory getManagedAccessFactory();

    public List<JPASchemaUpdateStategy> getUpdates();

}
