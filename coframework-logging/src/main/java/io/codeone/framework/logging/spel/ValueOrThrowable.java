package io.codeone.framework.logging.spel;

import lombok.Getter;

/**
 * Used to cache the result of a computation, or any exception it throws, if
 * either of them are going to be used later. The result and the exception,
 * only one of them can have non-null value, the other will be null.
 */
@Getter
public class ValueOrThrowable<R, T extends Throwable> {

    /**
     * The normal result of a computation.
     */
    private final R value;

    /**
     * The exception the computation throws.
     */
    private final T throwable;

    public ValueOrThrowable(R value) {
        this.value = value;
        this.throwable = null;
    }

    public ValueOrThrowable(T t) {
        this.value = null;
        this.throwable = t;
    }

    public boolean isThrowable() {
        return throwable != null;
    }

    public void throwIfThrowable() throws T {
        if (throwable != null) {
            throw throwable;
        }
    }
}
