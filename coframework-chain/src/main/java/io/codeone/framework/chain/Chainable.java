package io.codeone.framework.chain;

import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.MDC;

@FunctionalInterface
public interface Chainable {

    boolean execute(Context context);

    default boolean run(Context context) {
        return MDC.wrap(() -> {
            long start = System.currentTimeMillis();
            Object resultOrException = null;
            try {
                if (context.onEnterChainable() != null) {
                    context.onEnterChainable().accept(context);
                }
                boolean toContinue = execute(context);
                resultOrException = toContinue;
                return toContinue;
            } catch (Exception e) {
                resultOrException = e;
                throw e;
            } finally {
                long elapsed = System.currentTimeMillis() - start;
                MDC.log(context, this, resultOrException, elapsed);
            }
        });
    }

    default <T> T run(Context context, Object resultKey) {
        run(context);
        return context.getParam(resultKey);
    }
}
