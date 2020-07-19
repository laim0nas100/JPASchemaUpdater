/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jpaschemaupdater.other;

import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 *
 * @author Lemmin
 */
public interface PopulatorConfigurer extends Ordered {
    void configure(ResourceDatabasePopulator var1, Resource var2);
}
