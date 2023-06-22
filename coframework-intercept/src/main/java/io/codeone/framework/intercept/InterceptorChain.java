package io.codeone.framework.intercept;

import io.codeone.framework.intercept.util.Context;
import io.codeone.framework.intercept.util.Interception;
import io.codeone.framework.intercept.util.Invokable;

import java.lang.reflect.Method;
import java.util.*;

public abstract class InterceptorChain<T extends Interceptor<?>> {

    private List<T> interceptors = new ArrayList<>();

    public void addInterceptor(T interceptor) {
        interceptors.add(interceptor);
    }

    public void sortInterceptors() {
        interceptors.sort(Comparator
                .comparing(o -> Optional.ofNullable(
                                o.getClass().getAnnotation(Intercept.class))
                        .map(Intercept::value)
                        .map(Stage::order)
                        .orElse(Integer.MAX_VALUE))
                .thenComparing(o -> Optional.ofNullable(
                                o.getClass().getAnnotation(Intercept.class))
                        .map(Intercept::order)
                        .orElse(Integer.MAX_VALUE)));
    }

    public Object intercept(Method method, Object[] args,
                            Invokable<Object> invokable) throws Throwable {
        if (interceptors == null || interceptors.isEmpty()) {
            return invokable.invoke();
        }

        Context context = new Context(method, args);

        LinkedList<Interception<?>> stack = new LinkedList<>();

        try {
            before(stack, context);

            context.setResult(invokable.invoke());
        } catch (Throwable t) {
            context.setError(t);
        }

        after(stack, context);

        return context.getResultOrThrow();
    }

    private void before(LinkedList<Interception<?>> stack, Context context)
            throws Throwable {
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
