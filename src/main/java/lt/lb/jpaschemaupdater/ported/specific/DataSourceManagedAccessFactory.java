package lt.lb.jpaschemaupdater.ported.specific;

import javax.sql.DataSource;
import lt.lb.jpaschemaupdater.ported.ManagedAccess;
import lt.lb.jpaschemaupdater.ported.ManagedAccess.ConnProvider;
import lt.lb.jpaschemaupdater.ported.ManagedAccessFactory;

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
