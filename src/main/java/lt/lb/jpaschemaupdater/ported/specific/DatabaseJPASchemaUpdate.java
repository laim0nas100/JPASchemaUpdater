package lt.lb.jpaschemaupdater.ported.specific;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import lt.lb.jpaschemaupdater.ported.JPASchemaUpdateException;
import lt.lb.jpaschemaupdater.ported.JPASchemaVersionResolver;

/**
 *
 * @author laim0nas100
 */
public class DatabaseJPASchemaUpdate implements JPASchemaVersionResolver {

    protected DataSource source;
    protected String versionSelectQuery;
    protected String versionUpdatePreparedStatement;
    
    public DatabaseJPASchemaUpdate(){
        
    }

    /**
     * 
     * @param source DataSource
     * @param versionSelectQuery query that returns single result Long
     * @param versionUpdatePreparedStatement prepared statement query that accepts 1 Long parameter
     */
    public DatabaseJPASchemaUpdate(DataSource source, String versionSelectQuery, String versionUpdatePreparedStatement) {
        this.source = source;
        this.versionSelectQuery = versionSelectQuery;
        this.versionUpdatePreparedStatement = versionUpdatePreparedStatement;
    }

    public DataSource getSource() {
        return source;
    }

    public void setSource(DataSource source) {
        this.source = source;
    }

    public String getVersionSelectQuery() {
        return versionSelectQuery;
    }

    public void setVersionSelectQuery(String versionSelectQuery) {
        this.versionSelectQuery = versionSelectQuery;
    }

    public String getVersionUpdatePreparedStatement() {
        return versionUpdatePreparedStatement;
    }

    public void setVersionUpdatePreparedStatement(String versionUpdatePreparedStatement) {
        this.versionUpdatePreparedStatement = versionUpdatePreparedStatement;
    }

     

    @Override
    public Long getCurrentVersion() {
        Connection connection = null;
        Long version = -1L;
        try {
            connection = source.getConnection();
            Statement st = connection.createStatement();
            version = st.executeQuery(versionSelectQuery).getLong(1);
        } catch (Exception ex) {
            throw new JPASchemaUpdateException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new JPASchemaUpdateException(ex);
                }
            }
        }
        return version;

    }

    @Override
    public void setCurrentVersion(Long version) {
        Connection connection = null;
        try {
            connection = source.getConnection();
            PreparedStatement st = connection.prepareStatement(versionUpdatePreparedStatement);
            st.setLong(1, version);
            st.execute();
        } catch (Exception ex) {
            throw new JPASchemaUpdateException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    throw new JPASchemaUpdateException(ex);
                }
            }
        }
    }

}
