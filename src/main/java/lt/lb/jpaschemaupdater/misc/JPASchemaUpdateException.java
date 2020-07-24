package lt.lb.jpaschemaupdater.misc;

/**
 *
 * @author laim0nas100
 */
public class JPASchemaUpdateException extends RuntimeException {

    public JPASchemaUpdateException(String message) {
        super(message);
    }

    public JPASchemaUpdateException(Throwable cause) {
        super(cause);
    }

    public JPASchemaUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

}
