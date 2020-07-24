package lt.lb.jpaschemaupdater;

import java.util.List;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaUpdateInstanceMaker<Ver> {

    public List<JPASchemaUpdateInstance<Ver>> getInstances();
}
