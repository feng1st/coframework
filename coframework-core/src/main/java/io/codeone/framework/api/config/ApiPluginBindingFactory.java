package io.codeone.framework.api.config;

import io.codeone.framework.api.API;
import io.codeone.framework.logging.plugin.LoggingPlugin;
import io.codeone.framework.plugin.AnnoPluginBinding;
import io.codeone.framework.plugin.AnnoPluginBindingFactory;

import java.util.Collections;
import java.util.List;

public class ApiPluginBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Collections.singletonList(AnnoPluginBinding.of(API.class, LoggingPlugin.class));
    }
}
