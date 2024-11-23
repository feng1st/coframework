package io.codeone.framework.plugin.autoconfigure;

import io.codeone.framework.plugin.AnnoPluginBindingFactory;
import io.codeone.framework.plugin.binding.AnnoPluginBindingRepo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

@Configuration
@ComponentScan("io.codeone.framework.plugin")
public class CoFrameworkPluginAutoConfiguration {

    static {

        List<AnnoPluginBindingFactory> factories = SpringFactoriesLoader.loadFactories(
                AnnoPluginBindingFactory.class, null);

        factories.forEach(factory -> AnnoPluginBindingRepo.STATIC_BINDINGS.addAll(factory.getBindings()));
    }
}
