package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public abstract class BaseAbilityScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        Ability ability = extensibleClass.getAnnotation(Ability.class);
        scanAbility(AbilityDef.of(ability, extensibleClass));
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        Ability.Method abilityMethod = method.getAnnotation(Ability.Method.class);
        scanAbilityMethod(AbilityMethod.of(abilityMethod, method));
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario bizScenario) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        scanAbilityImpl(AbilityImpl.of(method, implementingClass, bizScenario));
    }

    protected void scanAbility(AbilityDef abilityDef) {
    }

    protected void scanAbilityMethod(AbilityMethod abilityMethod) {
    }

    protected void scanAbilityImpl(AbilityImpl abilityImpl) {
    }
}
