package io.codeone.framework.plugin.binding;

import io.codeone.framework.plugin.Plugin;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface EnablePlugin {

    Class<? extends Plugin>[] value();
}
