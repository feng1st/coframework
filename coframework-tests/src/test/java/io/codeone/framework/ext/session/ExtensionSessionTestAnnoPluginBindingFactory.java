package io.codeone.framework.ext.session;

import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import io.codeone.framework.plugin.binding.AnnoPluginBindingFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ExtensionSessionTestAnnoPluginBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Collections.singletonList(
                AnnoPluginBinding.of(ExtensionSessionTestAnno.class, ExtensionSessionPlugin.class));
    }
}
