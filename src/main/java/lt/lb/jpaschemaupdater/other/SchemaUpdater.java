/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jpaschemaupdater.other;

import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author Lemmin
 */
public interface SchemaUpdater {
    boolean update(Long var1);

    boolean update(Long var1, PlatformTransactionManager var2);

    void register(DatabaseVersionUpdater var1);
}
