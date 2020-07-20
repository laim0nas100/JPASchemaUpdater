package lt.lb.jpaschemaupdater.ported;

/**
 *
 * @author laim0nas100
 */
public interface JPASchemaVersionResolver {
    public Long getCurrentVersion();

    public void setCurrentVersion(Long version);
}
