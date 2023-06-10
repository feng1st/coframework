package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

public abstract class BaseExtensionPointScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        ExtensionPoint extensionPoint = getExtensionPoint(extensibleClass);
        if (extensionPoint == null) {
            return;
        }
        scanExtensionPoint(extensibleClass, extensionPoint);
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        ExtensionPoint extensionPoint = getExtensionPoint(extensibleClass);
        if (extensionPoint == null) {
            return;
        }
        scanExtensionPointMethod(extensibleClass, extensionPoint, method);
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
        ExtensionPoint extensionPoint = getExtensionPoint(extensibleClass);
        if (extensionPoint == null) {
            return;
        }
        scanExtensionPointImpl(extensibleClass, extensionPoint, method, methodDeclaringClass, methodBizScenario);
    }

    private ExtensionPoint getExtensionPoint(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(ExtensionPoint.class);
    }

    protected void scanExtensionPoint(Class<?> extensibleClass, ExtensionPoint extensionPoint) {
    }

    protected void scanExtensionPointMethod(Class<?> extensibleClass, ExtensionPoint extensionPoint, Method method) {
    }

    protected void scanExtensionPointImpl(Class<?> extensibleClass, ExtensionPoint extensionPoint, Method method,
                                          Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
    }
}
