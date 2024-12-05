package io.codeone.framework.chain;

import io.codeone.framework.chain.context.Context;
import io.codeone.framework.chain.log.MDC;

/**
 * Represents a unit of execution in a chain of operations.
 *
 * <p>Classes implementing this interface define a specific action that can be executed
 * within a given {@link Context}. The {@link #run(Context)} and {@link #run(Context,
 * Object)} methods are the primary entry points for executing the chainable unit.
 * The execution is wrapped with scoped logging provided by {@link MDC} to ensure
 * consistent logging of execution details. The {@link #execute(Context)} method
 * is the core logic that must be implemented by developers to define the behavior
 * of the chainable unit.
 */
@FunctionalInterface
public interface Chainable {

    /**
     * Executes the core logic of this chainable unit.
     *
     * <p>Developers should implement this method to define the actual operation
     * to be performed by this unit. This method is invoked internally by the {@link
     * #run(Context)} methods and is not intended to handle logging or external
     * concerns.
     *
     * @param context the context in which the execution occurs
     * @return {@code true} to continue the chain, {@code false} to stop the chain
     */
    boolean execute(Context context);

    /**
     * Executes this chainable unit as an entry point, wrapping the execution with
     * scoped logging.
     *
     * <p>Uses {@link MDC} to log execution details such as elapsed time and any
     * exceptions encountered during execution. This method serves as the primary
     * entry point for triggering execution.
     *
     * @param context the context in which the execution occurs
     * @return {@code true} to continue the chain, {@code false} to stop the chain
     */
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

    /**
     * Executes this chainable unit as an entry point and retrieves a specific result
     * from the context.
     *
     * <p>Like {@link #run(Context)}, this method wraps the execution with scoped
     * logging using {@link MDC}. It is typically used when the execution produces
     * a value stored in the context under a specified key.
     *
     * @param context   the context in which the execution occurs
     * @param resultKey the key identifying the result in the context
     * @param <T>       the type of the result
     * @return the result associated with the specified key
     */
    default <T> T run(Context context, Object resultKey) {
        run(context);
        return context.getParam(resultKey);
    }
}
