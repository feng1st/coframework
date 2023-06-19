package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;

import java.util.Objects;

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
        AbilityDef that = (AbilityDef) o;
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

    private static String getName(Ability ability, Class<?> extensibleClass) {
        return ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
    }
}
