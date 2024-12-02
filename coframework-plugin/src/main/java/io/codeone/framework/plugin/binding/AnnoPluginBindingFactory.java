package io.codeone.framework.plugin.binding;

import java.util.List;

/**
 * A factory interface for creating annotation-to-plugin bindings.
 *
 * <p>Implementations of this interface provide a list of {@link AnnoPluginBinding}
 * instances that define how specific annotations are associated with plugins. These
 * factories are discovered and invoked by {@code CoFrameworkPluginAutoConfiguration}.
 */
public interface AnnoPluginBindingFactory {

    /**
     * Retrieves the list of annotation-to-plugin bindings.
     *
     * @return a list of {@link AnnoPluginBinding} instances
     */
    List<AnnoPluginBinding> getBindings();
}
