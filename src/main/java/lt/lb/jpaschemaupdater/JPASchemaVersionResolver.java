package lt.lb.jpaschemaupdater;

import java.util.Comparator;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaVersionResolver<Ver> {

    public Ver getCurrentVersion();

    public void setCurrentVersion(Ver version);
    
    public Comparator<Ver> getVersionComparator();
}
