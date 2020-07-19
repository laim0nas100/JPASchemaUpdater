//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package lt.lb.jpaschemaupdater.other;

import org.springframework.core.PriorityOrdered;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

public class DefaultSeparatorConfigurerImpl implements PopulatorConfigurer, PriorityOrdered {
    private static final String DEFAULT_SEPARATOR = ";";

    public DefaultSeparatorConfigurerImpl() {
    }

    @Override
    public void configure(ResourceDatabasePopulator populator, Resource resource) {
        Assert.notNull(populator, "Parameter 'populator' cannot be null.");
        populator.setSeparator(";");
    }

    @Override
    public int getOrder() {
        return -2147483648;
    }
}
