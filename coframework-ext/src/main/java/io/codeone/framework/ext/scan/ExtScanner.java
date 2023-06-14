package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface ExtScanner {

    default void scanExtensible(Class<?> extensibleClass) {
    }

    default void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
    }

    default void scanExtension(Class<?> extensibleClass, Method method,
                               Class<?> implementingClass, BizScenario bizScenario) {
    }
}
