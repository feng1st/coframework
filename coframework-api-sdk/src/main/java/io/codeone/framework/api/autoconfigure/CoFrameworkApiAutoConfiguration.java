package io.codeone.framework.api.autoconfigure;

import io.codeone.framework.api.converter.ApiErrorCodeConverter;
import io.codeone.framework.api.converter.ApiResultConverter;
import io.codeone.framework.api.util.ApiErrorCodeUtils;
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
 * for {@link ApiResultConverter} and {@link ApiErrorCodeConverter} to the conversion
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
        applicationContext.getBeansOfType(ApiErrorCodeConverter.class).values()
                .forEach(ApiErrorCodeUtils.CONVERSION_SERVICE::addConverter);
    }
}
