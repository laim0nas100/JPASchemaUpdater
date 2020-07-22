package lt.lb.jpaschemaupdater.ported.specific;

import java.util.List;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateInstance;

/**
 *
 * @author laim0nas100
 */
public class JPASchemaUpdateInvoker {

    protected JPASchemaUpdateCommiter commiter;
    protected List<JPASchemaUpdateInstance> updates;

    public JPASchemaUpdateCommiter getCommiter() {
        return commiter;
    }

    public void setCommiter(JPASchemaUpdateCommiter commiter) {
        this.commiter = commiter;
    }

    public List<JPASchemaUpdateInstance> getUpdates() {
        return updates;
    }

    public void setUpdates(List<JPASchemaUpdateInstance> updates) {
        this.updates = updates;
    }

    public void invokeUpdate() throws Exception {
        commiter.updateSchema(updates);
    }
}
