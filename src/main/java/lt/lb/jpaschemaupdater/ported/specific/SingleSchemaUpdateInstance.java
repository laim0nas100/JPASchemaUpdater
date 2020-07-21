package lt.lb.jpaschemaupdater.ported.specific;

import java.util.Arrays;
import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ported.ManagedAccess;

/**
 *
 * @author laim0nas100
 */
public interface SingleSchemaUpdateInstance extends JPASchemaUpdateInstance {

    @Override
    public default List<JPASchemaUpdateStategy> getUpdates() {
        return Arrays.asList(a -> executeStrategy(a));
    }

    public void executeStrategy(ManagedAccess ma);

}
