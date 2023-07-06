package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.util.ScanModelUtils;
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
        this.code = ScanModelUtils.getClassKey(extensibleClass);
        this.name = ScanModelUtils.getName(ability, extensibleClass);
        this.description = ability.description();
    }

    @Override
    public String toString() {
        return name;
    }
}
