package io.codeone.framework.plugin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Annotation;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class AnnoPluginBinding {

    private final Class<? extends Annotation> annoType;

    private final Class<? extends Plugin> pluginClass;
}
