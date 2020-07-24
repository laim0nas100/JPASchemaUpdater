package lt.lb.jpaschemaupdater.longver;

import java.util.Arrays;
import java.util.List;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.SingleSchemaUpdateInstance;

/**
 *
 * @author laim0nas100
 */
public interface SingleSchemaUpdateInstanceLong extends SingleSchemaUpdateInstance<Long> {

    @Override
    public default List<JPASchemaUpdateStategy> getUpdates() {
        return Arrays.asList(a -> executeStrategy(a));
    }

    @Override
    public default Long getVersion() {
        return version();
    }

    public long version();

}
