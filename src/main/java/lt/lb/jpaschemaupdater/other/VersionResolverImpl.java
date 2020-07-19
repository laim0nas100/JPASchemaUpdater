//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import org.apache.commons.lang3.CharUtils;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class VersionResolverImpl implements VersionResolver {
    public VersionResolverImpl() {
    }

    @Override
    public Long getVersion(Resource resource) {
        Assert.notNull(resource, "Parameter 'resource' cannot be null.");
        StringBuilder versionNumberBuilder = new StringBuilder();
        char[] arr$ = resource.getFilename().toCharArray();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            char c = arr$[i$];
            if (!CharUtils.isAsciiNumeric(c)) {
                break;
            }

            versionNumberBuilder.append(c);
        }

        String versionString = versionNumberBuilder.toString();
        return Long.parseLong(versionString, 10);
    }
}
