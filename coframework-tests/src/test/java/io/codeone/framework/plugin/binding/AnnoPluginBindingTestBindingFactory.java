package io.codeone.framework.plugin.binding;

import java.util.Collections;
import java.util.List;

public class AnnoPluginBindingTestBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Collections.singletonList(AnnoPluginBinding.of(AnnoPluginBindingTestAnno.class,
                AnnoPluginBindingTestPluginBar.class));
    }
}
