package io.codeone.framework.plugin.autoconfigure;

import io.codeone.framework.plugin.binding.AnnoPluginBindingFactory;
import io.codeone.framework.plugin.binding.repo.AnnoPluginBindingRepo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

/**
 * Autoconfiguration for Co-Framework plugins.
 *
 * <p>This configuration class scans and registers plugin-related components. It
 * also loads all {@link AnnoPluginBindingFactory} implementations using {@code
 * SpringFactoriesLoader} and populates the {@link AnnoPluginBindingRepo} with their
 * provided bindings.
 */
@Configuration
@ComponentScan("io.codeone.framework.plugin")
public class CoFrameworkPluginAutoConfiguration {

    static {
        // Load all AnnoPluginBindingFactory implementations and populate the repository
        List<AnnoPluginBindingFactory> factories = SpringFactoriesLoader.loadFactories(
                AnnoPluginBindingFactory.class, null);

        factories.forEach(factory -> AnnoPluginBindingRepo.STATIC_BINDINGS.addAll(factory.getBindings()));
    }
}
