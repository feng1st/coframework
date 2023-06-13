package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

public abstract class BaseExtensionPointScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        scanExtensionPoint(extPt, extensibleClass);
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanExtensionPointMethod(extPt, extMethod, extensibleClass, method);
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario workingBizScenario) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanExtensionPointImpl(extPt, extMethod, extensibleClass, method, implementingClass, workingBizScenario);
    }

    private ExtensionPoint getExtensionPoint(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(ExtensionPoint.class);
    }

    protected void scanExtensionPoint(ExtensionPoint extPt, Class<?> extensibleClass) {
    }

    protected void scanExtensionPointMethod(ExtensionPoint extPt, ExtMethod extMethod,
                                            Class<?> extensibleClass, Method method) {
    }

    protected void scanExtensionPointImpl(ExtensionPoint extPt, ExtMethod extMethod,
                                          Class<?> extensibleClass, Method method,
                                          Class<?> implementingClass, BizScenario workingBizScenario) {
    }
}
