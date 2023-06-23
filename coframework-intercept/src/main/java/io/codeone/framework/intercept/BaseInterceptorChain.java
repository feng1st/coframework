package io.codeone.framework.intercept;

import io.codeone.framework.intercept.util.Context;
import io.codeone.framework.intercept.util.Interception;
import io.codeone.framework.intercept.util.Invokable;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseInterceptorChain implements InterceptorChain {

    @Override
    public Object intercept(Method method, Object[] args,
                            Invokable<Object> invokable) throws Throwable {
        List<Interceptor<?>> interceptors = getInterceptors();
        if (interceptors == null || interceptors.isEmpty()) {
            return invokable.invoke();
        }

        Context context = new Context(method, args);

        LinkedList<Interception<?>> stack = new LinkedList<>();

        try {
            before(stack, context, interceptors);

            context.setResult(invokable.invoke());
        } catch (Throwable t) {
            context.setError(t);
        }

        after(stack, context);

        return context.getResultOrThrow();
    }

    protected abstract List<Interceptor<?>> getInterceptors();

    private void before(LinkedList<Interception<?>> stack, Context context,
                        List<Interceptor<?>> interceptors) throws Throwable {
        for (Interceptor<?> interceptor : interceptors) {
            Interception<?> interception = new Interception<>(interceptor);
            stack.push(interception);
            interception.before(context);
        }
    }

    private void after(LinkedList<Interception<?>> stack, Context context) {
        while (!stack.isEmpty()) {
            stack.pop().after(context);
        }
    }
}
