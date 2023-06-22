package io.codeone.framework.intercept.util;

import java.lang.reflect.Method;

public class Context {

    private final Signature signature;

    private final Object[] args;

    private Object result;

    private Throwable error;

    public Context(Method method, Object[] args) {
        this.signature = SignatureFactory.get(method);
        this.args = args;
    }

    public Signature getSignature() {
        return signature;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = signature.getReturnType().cast(result);
        this.error = null;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
        this.result = null;
    }

    public Object getResultOrThrow() throws Throwable {
        if (error != null) {
            throw error;
        }
        return result;
    }
}
