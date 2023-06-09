package io.codeone.framework.ext.proxy;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ExtProxyRegistrar.class)
public class ExtProxyConfiguration {
}
