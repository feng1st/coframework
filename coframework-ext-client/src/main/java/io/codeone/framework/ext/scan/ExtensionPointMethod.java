package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public class ExtensionPointMethod {

    private final String classCode;

    private final String code;

    private final String name;

    private final String description;

    private final int order;

    public static ExtensionPointMethod of(ExtensionPoint.Method extPtMethod, Method method) {
        return new ExtensionPointMethod(extPtMethod, method);
    }

    public ExtensionPointMethod(ExtensionPoint.Method extPtMethod, Method method) {
        String classCode = getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + getMethodKey(method);
        this.name = getName(extPtMethod, method);
        this.description = getDescription(extPtMethod);
        this.order = getOrder(extPtMethod);
    }

    @Override
    public String toString() {
        return name;
    }

    private static String getClassKey(Method method) {
        return method.getDeclaringClass().getName();
    }

    private static String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }

    private static String getName(ExtensionPoint.Method extPtMethod, Method method) {
        return (extPtMethod == null || extPtMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : extPtMethod.name();
    }

    private static String getDescription(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? "" : extPtMethod.description();
    }

    private static int getOrder(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? -1 : extPtMethod.order();
    }
}
