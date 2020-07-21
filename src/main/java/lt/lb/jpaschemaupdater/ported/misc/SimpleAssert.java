package lt.lb.jpaschemaupdater.ported.misc;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author Lemmin
 */
public class SimpleAssert {
    
    public static <T> T notNull(T object){
        return Objects.requireNonNull(object);
    }

    public static String notBlank(String str) {
        not(str, StringUtils::isAllBlank, "Has to be not blank");
        return str;
    }

    public static <T> T not(T object, AssertFunc<? super T> validate, String message) {
        if (validate.apply(object)) {
            throw new IllegalArgumentException(message);
        }
        return object;
    }

    public static <T> T is(T object, AssertFunc<? super T> validate, String message) {
        if (!validate.apply(object)) {
            throw new IllegalArgumentException(message);
        }

        return object;
    }

    public static interface AssertFunc<T> {

        public Boolean applyUnsafe(T args) throws Throwable;

        public default Boolean apply(T args) {
            try {
                return applyUnsafe(args);
            } catch (Throwable th) {
                throw new IllegalArgumentException(th);
            }
        }

    }
    
}
