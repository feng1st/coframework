package io.codeone.framework.api.autoconfigure;

import io.codeone.framework.api.API;
import io.codeone.framework.logging.plugin.LoggingPlugin;
import io.codeone.framework.plugin.binding.AnnoPluginBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for the Co-Framework core components.
 *
 * <p>Registers bindings between annotations and plugins to enable logging for API
 * components.
 */
@Configuration
@ComponentScan("io.codeone.framework.api")
public class CoFrameworkCoreAutoConfiguration {

    /**
     * Binds the {@link API} annotation to the {@link LoggingPlugin}.
     *
     * @return the binding for logging API invocations
     */
    @Bean
    public AnnoPluginBinding ApiLoggingBinding() {
        return AnnoPluginBinding.of(API.class, LoggingPlugin.class);
    }
}
