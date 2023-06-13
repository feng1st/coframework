package io.codeone.framework.ext.scan.extpt;

import io.codeone.framework.ext.ExtensionPoint;

public class ExtPtInfo {

    private final ExtensionPoint extPt;

    private final Class<?> extensibleClass;

    public static ExtPtInfo of(ExtensionPoint extPt, Class<?> extensibleClass) {
        return new ExtPtInfo(extPt, extensibleClass);
    }

    public ExtPtInfo(ExtensionPoint extPt, Class<?> extensibleClass) {
        this.extPt = extPt;
        this.extensibleClass = extensibleClass;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public String getName() {
        return extPt.name().isEmpty() ? extensibleClass.getSimpleName() : extPt.name();
    }

    public String getDescription() {
        return extPt.description();
    }

    @Override
    public String toString() {
        return extensibleClass.toString();
    }
}
