package io.codeone.framework.ext.util;

import lombok.experimental.UtilityClass;
import org.springframework.aop.support.AopUtils;

@UtilityClass
public class ClassUtils {

    public Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public Class<?> getTargetClass(Object obj) {
        if (AopUtils.isAopProxy(obj)) {
            return AopUtils.getTargetClass(obj);
        }
        return obj.getClass();
    }
}
