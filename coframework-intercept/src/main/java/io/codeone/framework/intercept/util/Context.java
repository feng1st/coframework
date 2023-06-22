package io.codeone.framework.intercept.util;

import java.lang.reflect.Method;

public class Context {

    private final TargetMethod method;

    private final Object[] args;

    private final Class<?> resultType;

    private Object result;

    private Throwable error;

    public Context(Method method, Object[] args) {
        this.method = TargetMethod.of(method);
        this.args = args;
        this.resultType = method.getReturnType();
    }

    public TargetMethod getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = resultType.cast(result);
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
