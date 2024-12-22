package io.codeone.framework.plugin.binding;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AnnoPluginBindingTestBindingConfig {

    @Bean
    public AnnoPluginBinding AnnoPluginBindingTestBinding() {
        return AnnoPluginBinding.of(AnnoPluginBindingTestAnno.class,
                AnnoPluginBindingTestPluginFoo.class);
    }
}
