package io.codeone.framework.ext.scan.extpt;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.ExtensionPoint;
import io.codeone.framework.ext.scan.BaseExtScanner;

import java.lang.reflect.Method;

public abstract class BaseExtensionPointScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        scanExtensionPoint(ExtPtInfo.of(extPt, extensibleClass));
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanExtensionPointMethod(ExtPtMethodInfo.of(extPt, extMethod, extensibleClass, method));
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario workingBizScenario) {
        ExtensionPoint extPt = getExtensionPoint(extensibleClass);
        if (extPt == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanExtensionPointImpl(ExtPtImplInfo.of(extPt, extMethod, extensibleClass, method,
                implementingClass, workingBizScenario));
    }

    private ExtensionPoint getExtensionPoint(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(ExtensionPoint.class);
    }

    protected void scanExtensionPoint(ExtPtInfo extPtInfo) {
    }

    protected void scanExtensionPointMethod(ExtPtMethodInfo extPtMethodInfo) {
    }

    protected void scanExtensionPointImpl(ExtPtImplInfo extPtImplInfo) {
    }
}
