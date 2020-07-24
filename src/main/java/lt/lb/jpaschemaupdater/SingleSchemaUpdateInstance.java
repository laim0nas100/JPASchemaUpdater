package lt.lb.jpaschemaupdater;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author laim0nas100
 */
public interface SingleSchemaUpdateInstance<Ver> extends JPASchemaUpdateInstance<Ver> {
     @Override
    public default List<JPASchemaUpdateStategy> getUpdates() {
        return Arrays.asList(a -> executeStrategy(a));
    }

    public void executeStrategy(ManagedAccess ma);
}
