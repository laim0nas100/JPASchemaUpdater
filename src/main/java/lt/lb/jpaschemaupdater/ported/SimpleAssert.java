package lt.lb.jpaschemaupdater.ported;

import lt.lb.commons.parsing.StringOp;

/**
 *
 * @author Lemmin
 */
public class SimpleAssert {

    public static String notBlank(String str) {
        not(str, StringOp::isAllBlank, "Has to be not blank");
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
