package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AbilityDef {

    private final String code;

    private final String name;

    private final String description;

    public static AbilityDef of(Ability ability, Class<?> extensibleClass) {
        return new AbilityDef(ability, extensibleClass);
    }

    public AbilityDef(Ability ability, Class<?> extensibleClass) {
        this.code = getClassKey(extensibleClass);
        this.name = getName(ability, extensibleClass);
        this.description = ability.description();
    }

    @Override
    public String toString() {
        return name;
    }

    private static String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    private static String getName(Ability ability, Class<?> extensibleClass) {
        return ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
    }
}
