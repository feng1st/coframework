package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface AnnoPluginBindingRepo {

    List<AnnoPluginBinding> STATIC_BINDINGS = new ArrayList<>();

    Set<Class<? extends Plugin>> getPluginClasses(Class<? extends Annotation> annoType);
}
