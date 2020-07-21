package lt.lb.jpaschemaupdater.ported;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdater {

    public JPASchemaUpdateInstanceMaker getInstanceMaker();

    public JPASchemaUpdateCommiter getUpdateCommiter();

    public default void doCommitUpdate() throws Exception {
        getUpdateCommiter().updateSchema(getInstanceMaker().getInstances());
    }
}
