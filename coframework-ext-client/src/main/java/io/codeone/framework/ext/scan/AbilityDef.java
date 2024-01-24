package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Description;
import io.codeone.framework.ext.util.ScanModelUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * This model is used to record all definitions of abilities by the
 * {@code BaseAbilityScanner} (in {@code coframework-ext}). You can extend the
 * {@code BaseAbilityScanner} to for example store and index these models on the
 * server side.
 */
@Getter
@EqualsAndHashCode
public class AbilityDef {

    /**
     * The code of this ability, which is equivalent to the class name of the
     * ability interface.
     */
    private final String code;

    private final String name;

    private final String description;

    public static AbilityDef of(Description description, Class<?> extensibleClass) {
        return new AbilityDef(description, extensibleClass);
    }

    public AbilityDef(Description description, Class<?> extensibleClass) {
        this.code = ScanModelUtils.getClassKey(extensibleClass);
        this.name = ScanModelUtils.getName(description, extensibleClass);
        this.description = description.description();
    }

    @Override
    public String toString() {
        return name;
    }
}
