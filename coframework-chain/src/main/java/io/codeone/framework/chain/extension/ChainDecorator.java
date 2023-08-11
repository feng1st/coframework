package io.codeone.framework.chain.extension;

import io.codeone.framework.chain.graph.Path;
import io.codeone.framework.chain.model.Context;
import io.codeone.framework.chain.node.Node;
import io.codeone.framework.chain.spec.ChainSpec;
import io.codeone.framework.model.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Carries the modification details to the spec of a chain, and any extra input
 * arguments the new added nodes need. These modifications will be applied to
 * the chain upon its creation and execution.
 * <p>
 * Please node: The Decorator in the class name is not the same as the
 * Decorator/Wrapper in Design Patterns.
 */
public class ChainDecorator {

    /**
     * New paths that should be added to the node graph of the chain.
     * <p>
     * A new path consists of new nodes to be added, and some existing nodes as
     * "anchors".
     */
    private List<Path<Class<? extends Node>>> paths;

    /**
     * Additional input arguments that the new nodes need, mapped by Key.
     */
    private Map<Key, Object> arguments;

    /**
     * Adds a new path to the node graph of the chain.
     * <p>
     * A new path consists of new nodes to be added, and some existing nodes as
     * "anchors".
     * <p>
     * This method should be used by the developers who extend the chain.
     */
    public ChainDecorator addPath(Path<Class<? extends Node>> path) {
        if (paths == null) {
            paths = new ArrayList<>();
        }
        paths.add(path);
        return this;
    }

    /**
     * Adds an argument the new nodes need, by the Key.
     * <p>
     * This method should be used by the developers who extend the chain.
     */
    public ChainDecorator addArgument(Key key, Object value) {
        if (arguments == null) {
            arguments = new HashMap<>();
        }
        arguments.put(key, value);
        return this;
    }

    /**
     * Copies arguments from an existing context.
     *
     * @param context an existing context containing the source arguments
     * @param keys    indicates which arguments should be copied
     * @return itself (chaining)
     */
    public ChainDecorator copyArgumentsFrom(Context<?> context, Key... keys) {
        if (arguments == null) {
            arguments = new HashMap<>();
        }
        for (Key key : keys) {
            arguments.put(key, context.getArgument(key));
        }
        return this;
    }

    /**
     * Applies all new paths to the chain spec, before the creation of the
     * chain.
     * <p>
     * This method should be used by the developers who provide the chain.
     */
    public ChainSpec decorate(ChainSpec chainSpec) {
        if (paths == null || paths.isEmpty()) {
            return chainSpec;
        }
        return ChainSpec.of(chainSpec, paths);
    }

    /**
     * Passes all additional arguments to the context, before the invocation of
     * the chain.
     * <p>
     * This method should be used by the developers who provide the chain.
     */
    public <T> Context<T> decorate(Context<T> context) {
        if (arguments != null && !arguments.isEmpty()) {
            arguments.forEach(context::setArgument);
        }
        return context;
    }
}
