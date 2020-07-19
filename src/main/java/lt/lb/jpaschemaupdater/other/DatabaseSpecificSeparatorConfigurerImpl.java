//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

public class DatabaseSpecificSeparatorConfigurerImpl implements PopulatorConfigurer {
    private static final String PARAM_SEPARATOR = "s";
    private static final String ORACLE_SEPARATOR = "/";
    private String separator = "/";

    public DatabaseSpecificSeparatorConfigurerImpl() {
    }

    @Override
    public void configure(ResourceDatabasePopulator populator, Resource resource) {
        Assert.notNull(populator, "Parameter 'populator' cannot be null.");
        Assert.notNull(resource, "Parameter 'resource' cannot be null.");
        String resourceName = FilenameUtils.removeExtension(resource.getFilename());
        if (resourceName.contains("s")) {
            populator.setSeparator(this.getSeparator());
        }

    }

    @Override
    public int getOrder() {
        return 2147483647;
    }

    public String getSeparator() {
        return this.separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
