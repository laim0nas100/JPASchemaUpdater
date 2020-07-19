/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jpaschemaupdater.other;

import org.springframework.core.io.Resource;

/**
 *
 * @author Lemmin
 */
public interface VersionResolver {
    Long getVersion(Resource var1);
}
