package lt.lb.jpaschemaupdater.longver;

import java.util.Comparator;
import lt.lb.jpaschemaupdater.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaVersionResolverLong extends JPASchemaVersionResolver<Long>{

    @Override
    public default Comparator<Long> getVersionComparator() {
        return Long::compare;
    }
    
}
