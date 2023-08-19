package io.codeone.framework.plugin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Used by spring-boot to scan components automatically.
 *
 * <p>TODO: Move to starter
 */
@Configuration
@ComponentScan("io.codeone.framework")
public class PluginAutoConfiguration {
}
