package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;

public class AbilityInfo {

    private final Ability ability;

    private final Class<?> extensibleClass;

    public static AbilityInfo of(Ability ability, Class<?> extensibleClass) {
        return new AbilityInfo(ability, extensibleClass);
    }

    public AbilityInfo(Ability ability, Class<?> extensibleClass) {
        this.ability = ability;
        this.extensibleClass = extensibleClass;
    }

    public Class<?> getExtensibleClass() {
        return extensibleClass;
    }

    public String getName() {
        return ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
    }

    public String getDescription() {
        return ability.description();
    }

    @Override
    public String toString() {
        return extensibleClass.toString();
    }
}
