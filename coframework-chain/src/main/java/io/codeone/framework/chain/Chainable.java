package io.codeone.framework.chain;

import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.ChainLogger;
import io.codeone.framework.chain.log.LoggingContext;

/**
 * Represents a unit of execution in a chain of operations.
 *
 * <p>Implementing classes define the specific behavior to be executed within a
 * given {@link Context}. This interface provides entry points for execution, allowing
 * integration into broader chains of operations.
 */
@FunctionalInterface
public interface Chainable {

    /**
     * Executes the core logic of this chainable unit.
     *
     * <p>This method must be implemented by developers to define the primary operation
     * of the unit. It is invoked internally by the {@link #run(Context)} methods
     * and should not handle external concerns such as logging.
     *
     * @param context the context in which execution occurs
     * @return {@code true} to continue the chain, {@code false} to halt it
     */
    boolean execute(Context context);

    /**
     * Executes this chainable unit with scoped logging as an entry point.
     *
     * <p>This method serves as the primary entry point for triggering execution,
     * wrapping the execution logic with logging and monitoring.
     *
     * @param context the context in which execution occurs
     * @return {@code true} to continue the chain, {@code false} to halt it
     */
    default boolean run(Context context) {
        return LoggingContext.invoke(() -> {
            long start = System.currentTimeMillis();
            Object resultOrException = null;
            try {
                if (context.onExecute() != null) {
                    context.onExecute().accept(context);
                }
                boolean toContinue = execute(context);
                resultOrException = toContinue;
                return toContinue;
            } catch (Exception e) {
                resultOrException = e;
                throw e;
            } finally {
                long elapsed = System.currentTimeMillis() - start;
                ChainLogger.log(context, this, resultOrException, elapsed);
            }
        });
    }

    /**
     * Executes this chainable unit and retrieves a result from the context.
     *
     * <p>Similar to {@link #run(Context)}, this method wraps execution with logging
     * but is designed to fetch a specific result stored in the context under a
     * given key.
     *
     * @param context   the context in which execution occurs
     * @param resultKey the key identifying the desired result in the context
     * @param <T>       the type of the result
     * @return the result associated with the given key
     */
    default <T> T run(Context context, Object resultKey) {
        run(context);
        return context.get(resultKey);
    }
}
