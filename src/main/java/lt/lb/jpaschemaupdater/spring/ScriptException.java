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
public class ScriptException extends RuntimeException {

    /**
     * Constructor for {@code ScriptException}.
     *
     * @param message the detail message
     */
    public ScriptException(String message) {
        super(message);
    }

    /**
     * Constructor for {@code ScriptException}.
     *
     * @param message the detail message
     * @param cause the root cause
     */
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

}
