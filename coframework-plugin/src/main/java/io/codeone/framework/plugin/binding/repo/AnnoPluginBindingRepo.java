package io.codeone.framework.plugin.binding.repo;

import io.codeone.framework.plugin.Plugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Repository for managing annotation-to-plugin bindings.
 *
 * <p>This interface allows querying plugins associated with specific annotations.
 * It also provides access to a static list of globally available bindings, populated
 * during the configuration process in {@code CoFrameworkPluginAutoConfiguration}.
 */
public interface AnnoPluginBindingRepo {

    /**
     * A static list of globally accessible annotation-to-plugin bindings.
     *
     * <p>This list is populated during the initialization process by {@code CoFrameworkPluginAutoConfiguration}.
     */
    List<AnnoPluginBinding> STATIC_BINDINGS = new ArrayList<>();

    /**
     * Retrieves the set of plugin classes bound to a specific annotation type.
     *
     * @param annoType the annotation type to query
     * @return a set of plugin classes associated with the specified annotation
     * type, or an empty set if no bindings exist
     */
    Set<Class<? extends Plugin>> getPluginClasses(Class<? extends Annotation> annoType);
}
