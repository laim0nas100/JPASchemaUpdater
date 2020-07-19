/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.lb.jpaschemaupdater.spring;

import org.springframework.core.io.support.EncodedResource;

/**
 *
 * @author Lemmin
 */
public class CannotReadScriptException extends ScriptException {

    /**
     * Construct a new {@code CannotReadScriptException}.
     *
     * @param resource the resource that cannot be read from
     * @param cause the underlying cause of the resource access failure
     */
    public CannotReadScriptException(EncodedResource resource, Throwable cause) {
        super("Cannot read SQL script from " + resource, cause);
    }
}
