package lt.lb.jpaschemaupdater.specific;

import javax.sql.DataSource;
import lt.lb.jpaschemaupdater.ManagedAccess;
import lt.lb.jpaschemaupdater.ManagedAccess.ConnProvider;
import lt.lb.jpaschemaupdater.ManagedAccessFactory;

/**
 *
 * @author laim0nas100
 */
public class DataSourceManagedAccessFactory implements ManagedAccessFactory {

    private DataSource dataSource;

    @Override
    public ManagedAccess create() throws Exception {
        return new ConnProvider(dataSource.getConnection());
    }

    public DataSourceManagedAccessFactory(DataSource ds) {
        this.dataSource = ds;
    }

    public DataSourceManagedAccessFactory() {
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
