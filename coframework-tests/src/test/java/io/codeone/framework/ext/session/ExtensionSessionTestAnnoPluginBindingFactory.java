package io.codeone.framework.ext.session;

import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import io.codeone.framework.plugin.binding.AnnoPluginBindingFactory;

import java.util.Collections;
import java.util.List;

public class ExtensionSessionTestAnnoPluginBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Collections.singletonList(
                AnnoPluginBinding.of(ExtensionSessionTestAnno.class, ExtensionSessionPlugin.class));
    }
}
