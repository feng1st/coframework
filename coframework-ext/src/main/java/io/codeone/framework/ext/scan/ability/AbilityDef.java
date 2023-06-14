package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.util.ExtUtils;

import java.util.Objects;

public class AbilityDef {

    private final String code;

    private final String name;

    private final String description;

    public static AbilityDef of(Ability ability, Class<?> extensibleClass) {
        return new AbilityDef(ability, extensibleClass);
    }

    public AbilityDef(Ability ability, Class<?> extensibleClass) {
        this.code = ExtUtils.getClassKey(extensibleClass);
        this.name = ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
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
}
