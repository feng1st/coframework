package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtensionPoint;

import java.lang.reflect.Method;

/**
 * Scanner for extension points. You can extend this class to for example store
 * and index these models on the server side.
 */
public abstract class BaseExtensionPointScanner extends BaseExtScanner {

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        ExtensionPoint extPt = extensibleClass.getAnnotation(ExtensionPoint.class);
        scanExtensionPoint(ExtensionPointDef.of(extPt, extensibleClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        ExtensionPoint.Method extPtMethod = method.getAnnotation(ExtensionPoint.Method.class);
        scanExtensionPointMethod(ExtensionPointMethod.of(extPtMethod, method));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario bizScenario) {
        if (!extensibleClass.isAnnotationPresent(ExtensionPoint.class)) {
            return;
        }
        scanExtensionPointImpl(ExtensionPointImpl.of(method, implementingClass, bizScenario));
    }

    /**
     * Lets you process the extension point definition the framework found.
     *
     * @param extensionPointDef the extension point definition the framework
     *                          found
     */
    protected void scanExtensionPoint(ExtensionPointDef extensionPointDef) {
    }

    /**
     * Lets you process the extension point method definition the framework
     * found.
     *
     * @param extensionPointMethod the extension point method definition the
     *                             framework found
     */
    protected void scanExtensionPointMethod(ExtensionPointMethod extensionPointMethod) {
    }

    /**
     * Lets you process the extension point method implementation the framework
     * found.
     *
     * @param extensionPointImpl the extension point method implementation the
     *                           framework found
     */
    protected void scanExtensionPointImpl(ExtensionPointImpl extensionPointImpl) {
    }
}
