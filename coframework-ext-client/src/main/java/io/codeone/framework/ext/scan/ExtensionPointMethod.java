package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.lang.reflect.Method;

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
        String classCode = ScanModelUtils.getClassKey(method);
        this.classCode = classCode;
        this.code = classCode + "." + ScanModelUtils.getMethodKey(method);
        this.name = ScanModelUtils.getName(extPtMethod, method);
        this.description = ScanModelUtils.getDescription(extPtMethod);
        this.order = ScanModelUtils.getOrder(extPtMethod);
    }

    @Override
    public String toString() {
        return name;
    }
}
