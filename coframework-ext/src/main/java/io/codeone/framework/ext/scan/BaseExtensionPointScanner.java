package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

public abstract class BaseExtensionPointScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        ExtensionPoint extPt = extensibleClass.getAnnotation(ExtensionPoint.class);
        scanExtPt(ExtPtDef.of(extPt, extensibleClass));
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        ExtensionPoint.Method extPtMethod = method.getAnnotation(ExtensionPoint.Method.class);
        scanExtPtMethod(ExtPtMethod.of(extPtMethod, method));
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario bizScenario) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        scanExtPtImpl(ExtPtImpl.of(method, implementingClass, bizScenario));
    }

    protected void scanExtPt(ExtPtDef extPtDef) {
    }

    protected void scanExtPtMethod(ExtPtMethod extPtMethod) {
    }

    protected void scanExtPtImpl(ExtPtImpl extPtImpl) {
    }
}
