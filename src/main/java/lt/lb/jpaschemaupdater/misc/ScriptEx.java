package lt.lb.jpaschemaupdater.misc;

/**
 *
 * @author laim0nas100
 */
public class ScriptEx extends JPASchemaUpdateException {

    /**
     * Constructor for {@code ScriptException}.
     *
     * @param message the detail message
     */
    public ScriptEx(String message) {
        super(message);
    }

    /**
     * Constructor for {@code ScriptException}.
     *
     * @param message the detail message
     * @param cause the root cause
     */
    public ScriptEx(String message, Throwable cause) {
        super(message, cause);
    }
}
