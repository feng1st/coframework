package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;

import java.util.Objects;

public class ExtensionPointDef {

    private final String code;

    private final String name;

    private final String description;

    public static ExtensionPointDef of(ExtensionPoint extPt, Class<?> extensibleClass) {
        return new ExtensionPointDef(extPt, extensibleClass);
    }

    public ExtensionPointDef(ExtensionPoint extPt, Class<?> extensibleClass) {
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
        ExtensionPointDef that = (ExtensionPointDef) o;
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
