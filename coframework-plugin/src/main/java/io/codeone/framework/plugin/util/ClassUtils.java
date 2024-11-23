package io.codeone.framework.plugin.util;

import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

@UtilityClass
public class ClassUtils {

    public Class<?> getTargetClass(Object obj) {
        if (AopUtils.isAopProxy(obj)) {
            return AopUtils.getTargetClass(obj);
        }
        return obj.getClass();
    }
}
