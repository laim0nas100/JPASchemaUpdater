package lt.lb.jpaschemaupdater.longver;

import java.net.URL;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.misc.Scripting;
import lt.lb.jpaschemaupdater.specific.ResourceSchemaUpdateInstance;

/**
 *
 * @author laim0nas100
 */
public class DefaultResourceSchemaUpdateInstanceMaker extends ResourceSchemaUpdateInstanceMaker<Long, DefaultResourceSchemaUpdateInstanceMaker> {

    @Override
    protected DefaultResourceSchemaUpdateInstanceMaker me() {
        return this;
    }

    @Override
    protected ResourceSchemaUpdateInstance<Long, ?> createSchemaUpdateInstance(URL url, Scripting.ScriptReadOptions options, ManagedAccessFactory factory) {
        return new ResourceSchemaUpdateInstanceLong(url, options, factory);
    }

}
