package io.codeone.framework.api.autoconfigure;

import io.codeone.framework.api.converter.ApiExceptionConverter;
import io.codeone.framework.api.converter.ApiResultConverter;
import io.codeone.framework.api.util.ApiExceptionUtils;
import io.codeone.framework.api.util.ApiResultUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.codeone.framework.api")
public class CoFrameworkApiAutoConfiguration implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(ApiResultConverter.class).values()
                .forEach(ApiResultUtils.CONVERSION_SERVICE::addConverter);
        applicationContext.getBeansOfType(ApiExceptionConverter.class).values()
                .forEach(ApiExceptionUtils.CONVERSION_SERVICE::addConverter);
    }
}
