package io.codeone.framework.api.autoconfigure;

import io.codeone.framework.api.API;
import io.codeone.framework.logging.plugin.LoggingPlugin;
import io.codeone.framework.plugin.AnnoPluginBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.codeone.framework.api")
public class CoFrameworkCoreAutoConfiguration {

    @Bean
    public AnnoPluginBinding ApiLoggingBinding() {
        return AnnoPluginBinding.of(API.class, LoggingPlugin.class);
    }
}
