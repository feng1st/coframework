package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;

/**
 * Represents a binding between an annotation and a plugin.
 *
 * <p>This class associates a specific annotation type with a plugin class, enabling
 * targeted interception based on annotations.
 */
@RequiredArgsConstructor(staticName = "of")
@Getter
public class AnnoPluginBinding {

    /**
     * The annotation type to be associated with the plugin.
     */
    private final Class<? extends Annotation> annoType;

    /**
     * The plugin class associated with the annotation type.
     */
    private final Class<? extends Plugin> pluginClass;
}
