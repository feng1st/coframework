package io.codeone.framework.plugin.util;

import java.lang.reflect.Method;

public class Context {

    private final MethodWrap methodWrap;

    private final Object[] args;

    private Object result;

    private Throwable error;

    public Context(Method method, Object[] args) {
        this.methodWrap = MethodWrapFactory.get(method);
        this.args = args;
    }

    public MethodWrap getMethodWrap() {
        return methodWrap;
    }

    public Object[] getArgs() {
        return args;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = methodWrap.getReturnType().cast(result);
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
