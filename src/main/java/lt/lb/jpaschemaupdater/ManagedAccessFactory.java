package lt.lb.jpaschemaupdater;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 *
 * @author laim0nas100
 */
public interface ManagedAccessFactory {
    
    public static <T> Supplier<T> unchecked(Callable<T> call) {
        Objects.requireNonNull(call);

        return () -> {
            try {
                return call.call();
            } catch (Exception ex) {
                throw new ManagedAccess.ConnEx(ex);
            }
        };

    }

    public ManagedAccess create() throws Exception;
}
