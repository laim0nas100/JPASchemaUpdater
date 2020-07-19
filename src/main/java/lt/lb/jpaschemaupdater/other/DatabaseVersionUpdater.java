package lt.lb.jpaschemaupdater.other;

import org.springframework.jdbc.datasource.init.DatabasePopulator;

public interface DatabaseVersionUpdater {
    Long getVersionFrom();

    DatabasePopulator getDatabasePopulator();
}
