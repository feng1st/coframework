package io.codeone.framework.util;

/**
 * Used to store the result of a computation, or a possible exception it may throw, usually in a cache, if both of them
 * are going to be used later.
 */
public class ValueOrThrowable<R, T extends Throwable> {

    private final R value;

    private final T t;

    public ValueOrThrowable(R value) {
        this.value = value;
        this.t = null;
    }

    public ValueOrThrowable(T t) {
        this.value = null;
        this.t = t;
    }

    public R getValue() {
        return value;
    }

    public void throwIfThrowable() throws T {
        if (t != null) {
            throw t;
        }
    }
}
