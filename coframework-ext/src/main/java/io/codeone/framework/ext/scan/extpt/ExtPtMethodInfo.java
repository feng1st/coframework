package io.codeone.framework.ext.scan.extpt;

import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

public class ExtPtMethodInfo {

    private final ExtensionPoint extPt;

    private final ExtMethod extMethod;

    private final Class<?> extensibleClass;

    private final Method method;

    public static ExtPtMethodInfo of(ExtensionPoint extPt, ExtMethod extMethod,
                                     Class<?> extensibleClass, Method method) {
        return new ExtPtMethodInfo(extPt, extMethod, extensibleClass, method);
    }

    public ExtPtMethodInfo(ExtensionPoint extPt, ExtMethod extMethod,
                           Class<?> extensibleClass, Method method) {
        this.extPt = extPt;
        this.extMethod = extMethod;
        this.extensibleClass = extensibleClass;
        this.method = method;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public Method getMethod() {
        return method;
    }

    public String getName() {
        if (extMethod != null && !extMethod.name().isEmpty()) {
            return extMethod.name();
        }
        return extensibleClass.getSimpleName() + "." + method.getName();
    }

    public String getDescription() {
        return extMethod == null ? "" : extMethod.description();
    }

    public int getOrder() {
        return extMethod == null ? -1 : extMethod.order();
    }

    @Override
    public String toString() {
        return method.toString();
    }
}
