package io.codeone.framework.ext.session;

import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtensionSessionTestConfig {

    @Bean
    public AnnoPluginBinding extensionSessionTestAnnoPluginBinding() {
        return AnnoPluginBinding.of(ExtensionSessionTestAnno.class, ExtensionSessionPlugin.class);
    }
}
