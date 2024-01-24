package io.codeone.framework.sdk.util;

import io.codeone.framework.sdk.converter.ApiErrorConverter;
import io.codeone.framework.sdk.converter.ApiParamConverter;
import io.codeone.framework.sdk.converter.ApiResultConverter;
import io.codeone.framework.sdk.converter.ResultConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class ApiConversionService {

    @Resource
    private ApplicationContext applicationContext;

    private final DefaultConversionService conversionService = new DefaultConversionService();

    @PostConstruct
    private void postConstruct() {
        applicationContext.getBeansOfType(ApiErrorConverter.class).values()
                .forEach(conversionService::addConverter);
        applicationContext.getBeansOfType(ApiParamConverter.class).values()
                .forEach(conversionService::addConverter);
        applicationContext.getBeansOfType(ApiResultConverter.class).values()
                .forEach(conversionService::addConverter);
        applicationContext.getBeansOfType(ResultConverter.class).values()
                .forEach(conversionService::addConverter);
    }

    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    @Nullable
    public <T> T convert(@Nullable Object source, Class<T> targetType) {
        if (source != null
                && conversionService.canConvert(source.getClass(), targetType)) {
            return conversionService.convert(source, targetType);
        }
        return null;
    }
}
