/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jpaschemaupdater.spring;

/**
 *
 * @author Lemmin
 */
public class UncategorizedScriptException extends ScriptException {

    public UncategorizedScriptException(String message) {
        super(message);
    }

    /**
     * Construct a new {@code UncategorizedScriptException}.
     *
     * @param message detailed message
     * @param cause the root cause
     */
    public UncategorizedScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
