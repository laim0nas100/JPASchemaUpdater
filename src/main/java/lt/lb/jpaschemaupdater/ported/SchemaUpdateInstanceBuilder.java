package lt.lb.jpaschemaupdater.ported;

import java.util.List;
import java.util.function.Supplier;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 *
 * @author laim0nas100
 */
public abstract class SchemaUpdateInstanceBuilder<T extends SchemaUpdateInstanceBuilder> {
    private List<JPASchemaUpdateStategy> strategies;
    private Supplier<ManagedAccess> managedAccessSupplier;
    
    private DataSource dataSource;
    private EntityManagerFactory factory;
    private JPASchemaUpdateCommiter jpaSchemaUpdate;

    public List<JPASchemaUpdateStategy> getStrategies() {
        return strategies;
    }
    
    public JPASchemaUpdateCommiter getJpaSchemaUpdate() {
        return jpaSchemaUpdate;
    }

    public void setJpaSchemaUpdate(JPASchemaUpdateCommiter jpaSchemaUpdate) {
        this.jpaSchemaUpdate = jpaSchemaUpdate;
    }
    
    
    
    public T addStrategy(JPASchemaUpdateStategy... strategy){
        for(JPASchemaUpdateStategy s:strategy){
            strategies.add(s);
        }
        return (T) this;
    }

    public void setStrategies(List<JPASchemaUpdateStategy> strategies) {
        this.strategies = strategies;
    }

    public Supplier<ManagedAccess> getManagedAccessSupplier() {
        return managedAccessSupplier;
    }

    public void setManagedAccessSupplier(Supplier<ManagedAccess> managedAccessSupplier) {
        this.managedAccessSupplier = managedAccessSupplier;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }
    
    
    
    
    
    
    
    
}
