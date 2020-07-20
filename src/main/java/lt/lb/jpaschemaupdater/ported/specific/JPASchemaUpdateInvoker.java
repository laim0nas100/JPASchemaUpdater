package lt.lb.jpaschemaupdater.ported.specific;

import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;

/**
 *
 * @author laim0nas100
 */
public class JPASchemaUpdateInvoker {

    private JPASchemaUpdateCommiter commiter;
    private List<JPASchemaUpdateInstance> updates;
    
    public void invokeUpdate() {
        commiter.updateSchema(updates);
    }
}
