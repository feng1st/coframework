package io.codeone.framework.intercept.util;

import io.codeone.framework.intercept.Interceptor;

public class Interception<T> {

    private final Interceptor<T> interceptor;

    private T before;

    public Interception(Interceptor<T> interceptor) {
        this.interceptor = interceptor;
    }

    public void before(Context context) throws Throwable {
        before = interceptor.roundBefore(
                context.getMethodWrap(), context.getArgs());
    }

    public void after(Context context) {
        try {
            context.setResult(interceptor.after(
                    context.getMethodWrap(), context.getArgs(),
                    context.getResult(), context.getError(),
                    before));
        } catch (Throwable t) {
            context.setError(t);
        }
    }
}
