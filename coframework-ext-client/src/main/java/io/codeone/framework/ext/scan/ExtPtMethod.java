package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExtPtMethod {

    private final String classCode;

    private final String code;

    private final String name;

    private final String description;

    private final int order;

    public static ExtPtMethod of(ExtensionPoint.Method extPtMethod, Method method) {
        return new ExtPtMethod(extPtMethod, method);
    }

    public ExtPtMethod(ExtensionPoint.Method extPtMethod, Method method) {
        String classCode = getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + getMethodKey(method);
        this.name = (extPtMethod == null || extPtMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : extPtMethod.name();
        this.description = extPtMethod == null ? "" : extPtMethod.description();
        this.order = extPtMethod == null ? -1 : extPtMethod.order();
    }

    public String getClassCode() {
        return classCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtPtMethod that = (ExtPtMethod) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
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
                + "("
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(","))
                + ")";
    }
}
