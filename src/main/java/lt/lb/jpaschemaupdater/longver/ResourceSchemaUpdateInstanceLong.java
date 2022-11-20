package lt.lb.jpaschemaupdater.longver;

import java.net.URL;
import lt.lb.jpaschemaupdater.JPASchemaUpdateInstance;
import lt.lb.jpaschemaupdater.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.specific.ResourceSchemaUpdateInstance;
import lt.lb.jpaschemaupdater.misc.Scripting.ScriptReadOptions;

/**
 *
 * @author laim0nas100
 */
public class ResourceSchemaUpdateInstanceLong extends ResourceSchemaUpdateInstance<Long, ResourceSchemaUpdateInstanceLong> implements JPASchemaUpdateInstance<Long> {

    public ResourceSchemaUpdateInstanceLong() {
    }

    public ResourceSchemaUpdateInstanceLong(URL resource, ScriptReadOptions opt, ManagedAccessFactory managedAccessFactory) {
        super(ResourceReadingUtils.getVersionFromFileName(resource), resource, opt, managedAccessFactory);
    }


    @Override
    public void setResource(URL resource) {
        this.resource = resource;
        setVersion(ResourceReadingUtils.getVersionFromFileName(resource));
        this.updates = null;
    }

    @Override
    protected ResourceSchemaUpdateInstanceLong me() {
        return this;
    }

}
