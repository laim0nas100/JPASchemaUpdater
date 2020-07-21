package lt.lb.jpaschemaupdater.ported.specific;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import lt.lb.jpaschemaupdater.ported.misc.JPASchemaUpdateException;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateStategy.ConnectionSchemaUpdateStrategy;
import lt.lb.jpaschemaupdater.ported.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.ported.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.ported.misc.Scripting.ScriptReadOptions;

/**
 *
 * @author laim0nas100
 */
public class ResourceSchemaUpdateInstance<T extends BaseSchemaUpdateInstance<ResourceSchemaUpdateInstance>> extends BaseSchemaUpdateInstance<T> {

    protected URL resource;
    protected ScriptReadOptions opt;
    protected boolean continueOnError = false;
    protected boolean ignoreFailedDrops = false;

    public ResourceSchemaUpdateInstance() {
    }

    public ResourceSchemaUpdateInstance(URL resource, ScriptReadOptions opt, ManagedAccessFactory managedAccessFactory) throws URISyntaxException {
        super(ResourceReadingUtils.getVersionFromFileName(resource), managedAccessFactory);
        this.resource = resource;
        this.opt = opt;
    }

    public URL getResource() {
        return resource;
    }

    public void setResource(URL resource) throws URISyntaxException {
        this.resource = resource;
        setVersion(ResourceReadingUtils.getVersionFromFileName(resource));
        this.updates = null;
    }

    @Override
    public List<JPASchemaUpdateStategy> getUpdates() {
        if (updates == null) {
            try {
                //generate updates
                List<ConnectionSchemaUpdateStrategy> newUpdates = ResourceReadingUtils.fromResourceScript(resource, continueOnError, ignoreFailedDrops, opt);

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

    public ScriptReadOptions getOpt() {
        return opt;
    }

    public void setOpt(ScriptReadOptions opt) {
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
