package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ExtensionPointDef {

    private final String code;

    private final String name;

    private final String description;

    public static ExtensionPointDef of(ExtensionPoint extPt, Class<?> extensibleClass) {
        return new ExtensionPointDef(extPt, extensibleClass);
    }

    public ExtensionPointDef(ExtensionPoint extPt, Class<?> extensibleClass) {
        this.code = getClassKey(extensibleClass);
        this.name = getName(extPt, extensibleClass);
        this.description = extPt.description();
    }

    @Override
    public String toString() {
        return name;
    }

    private static String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    private static String getName(ExtensionPoint extPt, Class<?> extensibleClass) {
        return extPt.name().isEmpty() ? extensibleClass.getSimpleName() : extPt.name();
    }
}
