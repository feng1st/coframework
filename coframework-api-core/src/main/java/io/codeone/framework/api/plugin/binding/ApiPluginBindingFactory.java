package io.codeone.framework.api.plugin.binding;

import io.codeone.framework.api.API;
import io.codeone.framework.logging.plugin.LoggingPlugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import io.codeone.framework.plugin.binding.AnnoPluginBindingFactory;

import java.util.Collections;
import java.util.List;

/**
 * Registers annotation-to-plugin bindings to enable API component enhancements.
 */
public class ApiPluginBindingFactory implements AnnoPluginBindingFactory {

    /**
     * Binds the {@link LoggingPlugin} to the {@link API} annotation.
     *
     * @return list of bindings to register
     */
    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Collections.singletonList(AnnoPluginBinding.of(API.class, LoggingPlugin.class));
    }
}
