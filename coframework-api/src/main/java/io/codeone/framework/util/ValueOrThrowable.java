package io.codeone.framework.util;

/**
 * Used to cache the result of a computation, or any exception it throws, if
 * either of them are going to be used later. The result and the exception,
 * only one of them can have non-null value, the other will be null.
 */
public class ValueOrThrowable<R, T extends Throwable> {

    /**
     * The normal result of a computation.
     */
    private final R value;

    /**
     * The exception the computation throws.
     */
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

    public T getThrowable() {
        return t;
    }

    public boolean isThrowable() {
        return t != null;
    }

    public void throwIfThrowable() throws T {
        if (t != null) {
            throw t;
        }
    }
}
