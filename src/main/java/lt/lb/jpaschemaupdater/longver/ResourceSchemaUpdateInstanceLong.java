package lt.lb.jpaschemaupdater.longver;

import java.net.URL;
import lt.lb.jpaschemaupdater.misc.ResourceReadingUtils;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;
import lt.lb.jpaschemaupdater.specific.ResourceSchemaUpdateInstance;
import lt.lb.jpaschemaupdater.misc.Scripting.ScriptReadOptions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author laim0nas100
 */
public class ResourceSchemaUpdateInstanceLong extends ResourceSchemaUpdateInstance<Long> implements JPASchemaUpdateInstanceLong {

    public ResourceSchemaUpdateInstanceLong() {
    }

    public ResourceSchemaUpdateInstanceLong(URL resource, ScriptReadOptions opt, ManagedAccessFactory managedAccessFactory) {
        super(getVersionFromFileName(resource), resource, opt, managedAccessFactory);
    }

    public static Long getVersionFromFileName(URL url) {
        return ResourceReadingUtils.getFilePath(url)
                .map(FilenameUtils::getName)
                .map(FilenameUtils::removeExtension)
                .map(StringUtils::getDigits)
                .map(Long::parseLong).get();
    }

    @Override
    public void setResource(URL resource) {
        this.resource = resource;
        setVersion(getVersionFromFileName(resource));
        this.updates = null;
    }


}
