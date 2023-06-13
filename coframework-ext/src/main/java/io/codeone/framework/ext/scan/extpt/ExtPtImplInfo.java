package io.codeone.framework.ext.scan.extpt;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

public class ExtPtImplInfo {

    private final ExtensionPoint extPt;

    private final ExtMethod extMethod;

    private final Class<?> extensibleClass;

    private final Method method;

    private final Class<?> implementingClass;

    private final BizScenario workingBizScenario;

    public static ExtPtImplInfo of(ExtensionPoint extPt, ExtMethod extMethod,
                                   Class<?> extensibleClass, Method method,
                                   Class<?> implementingClass, BizScenario workingBizScenario) {
        return new ExtPtImplInfo(extPt, extMethod, extensibleClass, method,
                implementingClass, workingBizScenario);
    }

    public ExtPtImplInfo(ExtensionPoint extPt, ExtMethod extMethod,
                         Class<?> extensibleClass, Method method,
                         Class<?> implementingClass, BizScenario workingBizScenario) {
        this.extPt = extPt;
        this.extMethod = extMethod;
        this.extensibleClass = extensibleClass;
        this.method = method;
        this.implementingClass = implementingClass;
        this.workingBizScenario = workingBizScenario;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getImplementingClass() {
        return implementingClass;
    }

    public BizScenario getWorkingBizScenario() {
        return workingBizScenario;
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
        return implementingClass.toString();
    }
}
