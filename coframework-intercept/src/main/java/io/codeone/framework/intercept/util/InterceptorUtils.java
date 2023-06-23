package io.codeone.framework.intercept.util;

import io.codeone.framework.intercept.Intercept;
import io.codeone.framework.intercept.Interceptor;
import io.codeone.framework.intercept.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public final class InterceptorUtils {

    private InterceptorUtils() {
    }

    public static void sortInterceptors(
            List<? extends Interceptor<?>> interceptors) {
        interceptors.sort(Comparator
                .comparing(o -> Optional.ofNullable(
                                o.getClass().getAnnotation(Intercept.class))
                        .map(Intercept::value)
                        .map(Stage::order)
                        .orElse(Integer.MAX_VALUE))
                .thenComparing(o -> Optional.ofNullable(
                                o.getClass().getAnnotation(Intercept.class))
                        .map(Intercept::order)
                        .orElse(Integer.MAX_VALUE)));
    }
}
