package io.codeone.framework.api.converter;

import io.codeone.framework.api.util.ApiErrorUtils;
import io.codeone.framework.api.util.ApiParamUtils;
import io.codeone.framework.api.util.ApiResultUtils;
import io.codeone.framework.api.util.FailureUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Scans and registers API-related converters.
 */
@Component
public class ApiConverterLoader implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(ApiParamConverter.class).values()
                .forEach(ApiParamUtils.CONVERSION_SERVICE::addConverter);
        applicationContext.getBeansOfType(ApiResultConverter.class).values()
                .forEach(ApiResultUtils.CONVERSION_SERVICE::addConverter);
        applicationContext.getBeansOfType(ApiErrorConverter.class).values()
                .forEach(ApiErrorUtils.CONVERSION_SERVICE::addConverter);
        applicationContext.getBeansOfType(FailureConverter.class).values()
                .forEach(FailureUtils.CONVERSION_SERVICE::addConverter);
    }
}
