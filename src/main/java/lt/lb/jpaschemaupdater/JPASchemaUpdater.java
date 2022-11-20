package lt.lb.jpaschemaupdater;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author laim0nas100
 * @param <Ver>
 */
public interface JPASchemaUpdater<Ver> {

    public JPASchemaUpdateInstanceMaker<Ver> getInstanceMaker();

    public JPASchemaUpdateCommiter<Ver> getUpdateCommiter();

    public default void doCommitUpdate() throws Exception {
        getUpdateCommiter().updateSchema(getInstanceMaker().getInstances());
    }

    public default void doCommitUpdateCherryPick(Ver specificVersion, boolean writeVersion) throws Exception {
        List<JPASchemaUpdateInstance<Ver>> instances = getInstanceMaker().getInstances().stream()
                .filter(f -> Objects.equals(specificVersion, f.getVersion()))
                .collect(Collectors.toList());
        for(JPASchemaUpdateInstance instance:instances){
            getUpdateCommiter().doUpdate(instance);
        }
    }
}
