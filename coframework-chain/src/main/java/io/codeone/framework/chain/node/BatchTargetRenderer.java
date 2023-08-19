package io.codeone.framework.chain.node;

import io.codeone.framework.chain.logging.Logger;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.model.Key;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A renderer that renders a batch of targets at once. Usually because you need
 * to access an outer system that takes batch operations to gain performance
 * benefit while rendering. This class can be orchestrated with the
 * {@link IndividualTargetRenderer} which is a special
 * {@code BatchTargetRenderer} that its subclasses can handle each target
 * individually.
 *
 * @param <T> the type of the target
 */
public abstract class BatchTargetRenderer<T> extends TargetRenderer<List<T>> {

    private static final Key INDIVIDUAL_CONTEXT_MAP_KEY = new Key() {
        @Getter
        private final Class<Map> clazz = Map.class;
    };

    private Map<String, Context<T>> getIndividualContextMap(Context<?> context) {
        return context.computeArgumentIfAbsent(INDIVIDUAL_CONTEXT_MAP_KEY, HashMap::new);
    }

    /**
     * Returns its own context of the specified individual target.
     *
     * @param context the context of this batch renderer, which is the container
     *                of all these individual contexts
     * @param target  the user of the returned context
     * @return the individual context for the particular target
     */
    protected Context<T> getIndividualContext(Context<?> context, T target) {
        String individualKey = getIndividualKey(target);
        Map<String, Context<T>> individualContextMap = getIndividualContextMap(context);
        return individualContextMap.computeIfAbsent(individualKey, k -> Context.of(target));
    }

    /**
     * Returns its own logger of the specified individual target.
     *
     * @param logger logger of this node. We still use this logger except that
     *               we append a key to the log
     * @param target the user of the returned logger
     * @return the individual logger for the particular target
     */
    protected Logger getIndividualLogger(Logger logger, T target) {
        String individualKey = getIndividualKey(target);
        return (k, v) -> logger.log(k + "[" + individualKey + "]", v);
    }

    /**
     * Returns a unique key of an individual target. This key is needed to
     * access its own context and logger of the individual target.
     *
     * @param target the individual target
     * @return the unique key of the individual target
     */
    protected abstract String getIndividualKey(T target);
}
