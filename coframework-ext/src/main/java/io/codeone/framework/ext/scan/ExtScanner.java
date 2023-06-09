package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public interface ExtScanner {

    default void scanAbility(Class<?> extensibleClass) {
    }

    default void scanExtensionPoint(Class<?> extensibleClass) {
    }

    default void scanAbilityMethod(Class<?> extensibleClass, Method method) {
    }

    default void scanExtensionPointMethod(Class<?> extensibleClass, Method method) {
    }

    default void scanAbilityImpl(Class<?> extensibleClass, Method method,
                                 Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
    }

    default void scanExtensionPointImpl(Class<?> extensibleClass, Method method,
                                        Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
    }
}
