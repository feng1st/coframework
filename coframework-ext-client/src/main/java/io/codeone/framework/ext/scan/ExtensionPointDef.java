package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.ExtensionPoint;
import io.codeone.framework.ext.scan.util.ScanModelUtils;
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

    /**
     * The name of this extension point, which is {@link ExtensionPoint#name()}
     * if not null, or the simple class name of the extension point interface.
     */
    private final String name;

    /**
     * The description of this extension point, which is given in
     * {@link ExtensionPoint#description()}.
     */
    private final String description;

    /**
     * Constructs an {@code ExtensionPointDef} from the extension point
     * interface and its {@link ExtensionPoint} annotation.
     *
     * @param extPt           the {@code ExtensionPoint} annotation of the
     *                        extension point
     * @param extensibleClass the extension point interface
     * @return constructed {@code ExtensionPointDef}
     */
    public static ExtensionPointDef of(ExtensionPoint extPt, Class<?> extensibleClass) {
        return new ExtensionPointDef(extPt, extensibleClass);
    }

    /**
     * Constructs an {@code ExtensionPointDef} from the extension point
     * interface and its {@link ExtensionPoint} annotation.
     *
     * @param extPt           the {@code ExtensionPoint} annotation of the
     *                        extension point
     * @param extensibleClass the extension point interface
     */
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
