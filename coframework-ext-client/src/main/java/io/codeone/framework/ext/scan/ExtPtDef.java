package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;

import java.util.Objects;

public class ExtPtDef {

    private final String code;

    private final String name;

    private final String description;

    public static ExtPtDef of(ExtensionPoint extPt, Class<?> extensibleClass) {
        return new ExtPtDef(extPt, extensibleClass);
    }

    public ExtPtDef(ExtensionPoint extPt, Class<?> extensibleClass) {
        this.code = getClassKey(extensibleClass);
        this.name = extPt.name().isEmpty() ? extensibleClass.getSimpleName() : extPt.name();
        this.description = extPt.description();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExtPtDef that = (ExtPtDef) o;
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

    private static String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }
}
