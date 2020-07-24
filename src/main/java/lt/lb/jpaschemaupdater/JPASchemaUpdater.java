package lt.lb.jpaschemaupdater;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdater<Ver> {

    public JPASchemaUpdateInstanceMaker<Ver> getInstanceMaker();

    public JPASchemaUpdateCommiter<Ver> getUpdateCommiter();

    public default void doCommitUpdate() throws Exception {
        getUpdateCommiter().updateSchema(getInstanceMaker().getInstances());
    }
}
