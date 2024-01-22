package io.codeone.framework.plugin.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.lang.Nullable;
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

    @Nullable
    public <T> T convert(@Nullable Object source, Class<T> targetType) {
        if (source == null) {
            return null;
        }
        if (conversionService.isPresent()) {
            if (conversionService.get().canConvert(source.getClass(), targetType)) {
                return conversionService.get().convert(source, targetType);
            }
        } else {
            if (targetType.isInstance(source)) {
                return targetType.cast(source);
            }
        }
        return null;
    }
}
