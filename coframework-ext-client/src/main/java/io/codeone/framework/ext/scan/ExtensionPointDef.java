package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Description;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This model is used to record all definitions of extension points by the
 * {@code BaseExtensionPointScanner} (in {@code coframework-ext}). You can
 * extend the {@code BaseExtensionPointScanner} to for example store and index
 * these models on the server side.
 */
@Getter
@EqualsAndHashCode
public class ExtensionPointDef {

    /**
     * The code of this extension point, which is equivalent to the class name
     * of the extension point interface.
     */
    private final String code;

    private final String name;

    private final String description;

    public static ExtensionPointDef of(Description description, Class<?> extensibleClass) {
        return new ExtensionPointDef(description, extensibleClass);
    }

    public ExtensionPointDef(Description description, Class<?> extensibleClass) {
        this.code = ScanModelUtils.getClassKey(extensibleClass);
        this.name = ScanModelUtils.getName(description, extensibleClass);
        this.description = description.description();
    }

    @Override
    public String toString() {
        return name;
    }
}
