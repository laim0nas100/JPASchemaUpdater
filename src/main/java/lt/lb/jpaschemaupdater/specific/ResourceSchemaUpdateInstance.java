package lt.lb.jpaschemaupdater.specific;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lt.lb.jpaschemaupdater.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.misc.JPASchemaUpdateException;
import lt.lb.jpaschemaupdater.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.misc.Scripting;

/**
 *
 * @author laim0nas100
 */
public abstract class ResourceSchemaUpdateInstance<Ver, M extends ResourceSchemaUpdateInstance<Ver, M>> extends BaseSchemaUpdateInstance<Ver, M> {

    protected URL resource;
    protected Scripting.ScriptReadOptions opt;
    protected boolean continueOnError = false;
    protected boolean ignoreFailedDrops = false;

    public ResourceSchemaUpdateInstance() {
    }

    public ResourceSchemaUpdateInstance(Ver version, URL resource, Scripting.ScriptReadOptions opt, ManagedAccessFactory managedAccessFactory) {
        super(version, managedAccessFactory);
        this.resource = resource;
        this.opt = opt;
    }

    public URL getResource() {
        return resource;
    }

    public void setResource(URL resource) {
        this.resource = resource;
        this.updates = null;
    }

    @Override
    public List<JPASchemaUpdateStategy> getUpdates() {
        if (updates == null) {
            try {
                //generate updates
                List<JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy> newUpdates = ResourceReadingUtils.fromResourceScript(resource, continueOnError, ignoreFailedDrops, opt);

                updates = new ArrayList<>(newUpdates);

            } catch (Exception ex) {
                throw new JPASchemaUpdateException(ex);
            }
        }
        return updates;
    }

    @Override
    public void setUpdates(List<JPASchemaUpdateStategy> updates) {
        this.updates = updates;
    }

    public Scripting.ScriptReadOptions getOpt() {
        return opt;
    }

    public void setOpt(Scripting.ScriptReadOptions opt) {
        this.opt = opt;
    }

    public boolean isContinueOnError() {
        return continueOnError;
    }

    public void setContinueOnError(boolean continueOnError) {
        this.continueOnError = continueOnError;
    }

    public boolean isIgnoreFailedDrops() {
        return ignoreFailedDrops;
    }

    public void setIgnoreFailedDrops(boolean ignoreFailedDrops) {
        this.ignoreFailedDrops = ignoreFailedDrops;
    }
}
