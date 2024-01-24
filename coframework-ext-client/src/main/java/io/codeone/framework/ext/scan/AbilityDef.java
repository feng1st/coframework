package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.scan.util.ScanModelUtils;
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

    /**
     * The name of this ability, which is {@link Ability#name()} if not null, or
     * the simple class name of the ability interface.
     */
    private final String name;

    /**
     * The description of this ability, which is given in
     * {@link Ability#description()}.
     */
    private final String description;

    /**
     * Constructs an {@code AbilityDef} from the ability interface and its
     * {@link Ability} annotation.
     *
     * @param ability         the {@code Ability} annotation of the ability
     * @param extensibleClass the ability interface
     * @return constructed {@code AbilityDef}
     */
    public static AbilityDef of(Ability ability, Class<?> extensibleClass) {
        return new AbilityDef(ability, extensibleClass);
    }

    /**
     * Constructs an {@code AbilityDef} from the ability interface and its
     * {@link Ability} annotation.
     *
     * @param ability         the {@code Ability} annotation of the ability
     * @param extensibleClass the ability interface
     */
    public AbilityDef(Ability ability, Class<?> extensibleClass) {
        this.code = ScanModelUtils.getClassKey(extensibleClass);
        this.name = ScanModelUtils.getName(ability, extensibleClass);
        this.description = ability.description();
    }

    @Override
    public String toString() {
        return name;
    }
}
