package io.codeone.framework.api.autoconfigure;

import io.codeone.framework.api.converter.ApiErrorConverter;
import io.codeone.framework.api.converter.ApiResultConverter;
import io.codeone.framework.api.util.ApiErrorUtils;
import io.codeone.framework.api.util.ApiResultUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Autoconfiguration for the Co-Framework API components.
 *
 * <p>This configuration class scans and registers API-related beans and adds converters
 * for {@link ApiResultConverter} and {@link ApiErrorConverter} to the conversion
 * services.
 */
@Configuration
@ComponentScan("io.codeone.framework.api")
public class CoFrameworkApiAutoConfiguration implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(ApiResultConverter.class).values()
                .forEach(ApiResultUtils.CONVERSION_SERVICE::addConverter);
        applicationContext.getBeansOfType(ApiErrorConverter.class).values()
                .forEach(ApiErrorUtils.CONVERSION_SERVICE::addConverter);
    }
}
