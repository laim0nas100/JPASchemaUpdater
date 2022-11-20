package lt.lb.jpaschemaupdater.specific;

import java.util.List;
import lt.lb.jpaschemaupdater.JPASchemaUpdateCommiter;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;

/**
 *
 * @author laim0nas100
 * @param <Ver>
 */
public class JPASchemaUpdateInvoker<Ver> {

    protected JPASchemaUpdateCommiter<Ver> commiter;
    protected List<JPASchemaUpdateInstance<Ver>> updates;

    public JPASchemaUpdateCommiter getCommiter() {
        return commiter;
    }

    public void setCommiter(JPASchemaUpdateCommiter<Ver> commiter) {
        this.commiter = commiter;
    }

    public List<JPASchemaUpdateInstance<Ver>> getUpdates() {
        return updates;
    }

    public void setUpdates(List<JPASchemaUpdateInstance<Ver>> updates) {
        this.updates = updates;
    }

    public void invokeUpdate() throws Exception {
        commiter.updateSchema(updates);
    }
}
