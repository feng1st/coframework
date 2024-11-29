package io.codeone.framework.logging.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for the Co-Framework logging components.
 *
 * <p>Scans and registers logging-related beans, ensuring logging is properly configured
 * throughout the application.
 */
@Configuration
@ComponentScan("io.codeone.framework.logging")
public class CoFrameworkLoggingAutoConfiguration {
}
