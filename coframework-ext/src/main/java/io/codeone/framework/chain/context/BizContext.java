package io.codeone.framework.chain.context;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.BizScenarioParam;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * Represents an execution context for a chain that includes {@code Extensible}
 * {@code Chainable}.
 *
 * <p>If a {@code Chainable} in the chain is an {@code Extensible} interface (rather
 * than a concrete {@code Chainable} implementation), this context provides the
 * business identity and scenario necessary to route to a specific implementation
 * of the {@code Chainable}.
 */
@Accessors(chain = true)
public class BizContext extends Context implements BizScenarioParam {

    /**
     * The business identity and scenario associated with this context.
     */
    @Setter
    private BizScenario bizScenario;

    // Static factory methods

    /**
     * Creates a new empty {@code BizContext}.
     *
     * @return a new {@code BizContext} instance
     */
    public static BizContext of() {
        return new BizContext();
    }

    /**
     * Creates a new {@code BizContext} with a single key-value pair.
     *
     * @param k1 the key
     * @param v1 the value
     * @return a new {@code BizContext} instance with the given key-value pair
     */
    public static BizContext of(Object k1, Object v1) {
        BizContext context = new BizContext();
        context.paramMap.put(k1, v1);
        return context;
    }

    /**
     * Creates a new {@code BizContext} with two key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @return a new {@code BizContext} instance with the specified key-value pairs
     */
    public static BizContext of(Object k1, Object v1, Object k2, Object v2) {
        BizContext context = new BizContext();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        return context;
    }

    /**
     * Creates a new {@code BizContext} with three key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @return a new {@code BizContext} instance with the specified key-value pairs
     */
    public static BizContext of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        BizContext context = new BizContext();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        return context;
    }

    /**
     * Creates a new {@code BizContext} with four key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @param k4 the fourth key
     * @param v4 the fourth value
     * @return a new {@code BizContext} instance with the specified key-value pairs
     */
    public static BizContext of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4) {
        BizContext context = new BizContext();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        return context;
    }

    /**
     * Creates a new {@code BizContext} with five key-value pairs.
     *
     * @param k1 the first key
     * @param v1 the first value
     * @param k2 the second key
     * @param v2 the second value
     * @param k3 the third key
     * @param v3 the third value
     * @param k4 the fourth key
     * @param v4 the fourth value
     * @param k5 the fifth key
     * @param v5 the fifth value
     * @return a new {@code BizContext} instance with the specified key-value pairs
     */
    public static BizContext of(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3, Object k4, Object v4,
                                Object k5, Object v5) {
        BizContext context = new BizContext();
        context.paramMap.put(k1, v1);
        context.paramMap.put(k2, v2);
        context.paramMap.put(k3, v3);
        context.paramMap.put(k4, v4);
        context.paramMap.put(k5, v5);
        return context;
    }

    /**
     * Creates a new {@code BizContext} with a map of key-value pairs.
     *
     * @param paramMap the map containing key-value pairs
     * @return a new {@code BizContext} instance with the specified parameters
     */
    public static BizContext of(Map<Object, Object> paramMap) {
        BizContext context = new BizContext();
        context.paramMap.putAll(paramMap);
        return context;
    }

    /**
     * Creates a new {@code BizContext} as a copy of a {@code Context}.
     *
     * @param context the context to copy
     * @return a new {@code BizContext} instance with the same parameters as the
     * provided context
     */
    public static BizContext of(Context context) {
        return of(context.paramMap);
    }

    /**
     * Retrieves the business identity and scenario associated with this context.
     *
     * @return the {@link BizScenario}
     */
    @Override
    public BizScenario getBizScenario() {
        return bizScenario;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildLog(Map<String, Object> content) {
        super.buildLog(content);
        if (bizScenario != null) {
            content.put("bizId", bizScenario.getBizId());
            content.put("scenario", bizScenario.getScenario());
        }
    }
}
