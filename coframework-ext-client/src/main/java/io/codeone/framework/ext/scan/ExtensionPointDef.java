package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;
import io.codeone.framework.ext.util.ScanModelUtils;
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
        this.code = ScanModelUtils.getClassKey(extensibleClass);
        this.name = ScanModelUtils.getName(extPt, extensibleClass);
        this.description = extPt.description();
    }

    @Override
    public String toString() {
        return name;
    }
}
