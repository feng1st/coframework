package io.codeone.framework.plugin.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class ConversionServiceUtil {

    @Resource
    private Optional<ConversionService> conversionService;

    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return conversionService
                .map(o -> o.canConvert(sourceType, targetType))
                .orElseGet(() -> targetType.isAssignableFrom(sourceType));
    }

    public <T> Optional<T> convert(Object source, Class<T> targetType) {
        if (source == null) {
            return Optional.empty();
        }
        if (conversionService.isPresent()) {
            return conversionService
                    .filter(o -> o.canConvert(source.getClass(), targetType))
                    .map(o -> o.convert(source, targetType));
        } else {
            if (targetType.isAssignableFrom(source.getClass())) {
                return Optional.of(targetType.cast(source));
            }
        }
        return Optional.empty();
    }
}
