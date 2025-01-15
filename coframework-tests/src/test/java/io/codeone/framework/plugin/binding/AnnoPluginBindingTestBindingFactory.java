package io.codeone.framework.plugin.binding;

import java.util.Arrays;
import java.util.List;

public class AnnoPluginBindingTestBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Arrays.asList(
                AnnoPluginBinding.of(AnnoPluginBindingTestAnno.class, AnnoPluginBindingTestPluginFoo.class),
                AnnoPluginBinding.of(AnnoPluginBindingTestAnno.class, AnnoPluginBindingTestPluginBar.class));
    }
}
