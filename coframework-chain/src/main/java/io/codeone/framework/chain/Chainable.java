package io.codeone.framework.chain;

import io.codeone.framework.chain.context.Context;

@FunctionalInterface
public interface Chainable {

    boolean execute(Context context);

    default boolean run(Context context) {
        long start = System.currentTimeMillis();
        Object resultOrException = null;
        try {
            context.preExecute();
            boolean toContinue = execute(context);
            resultOrException = toContinue;
            return toContinue;
        } catch (Exception e) {
            resultOrException = e;
            throw e;
        } finally {
            long elapsed = System.currentTimeMillis() - start;
            context.postExecute(this, resultOrException, elapsed);
        }
    }

    default <T> T run(Context context, Object resultKey) {
        run(context);
        return context.getParam(resultKey);
    }
}
