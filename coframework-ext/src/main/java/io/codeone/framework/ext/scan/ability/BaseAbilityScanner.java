package io.codeone.framework.ext.scan.ability;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;
import io.codeone.framework.ext.scan.BaseExtScanner;

import java.lang.reflect.Method;

public abstract class BaseAbilityScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        scanAbility(AbilityInfo.of(ability, extensibleClass));
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanAbilityMethod(AbilityMethodInfo.of(ability, extMethod, extensibleClass, method));
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario workingBizScenario) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanAbilityImpl(AbilityImplInfo.of(ability, extMethod, extensibleClass, method,
                implementingClass, workingBizScenario));
    }

    private Ability getAbility(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(Ability.class);
    }

    protected void scanAbility(AbilityInfo abilityInfo) {
    }

    protected void scanAbilityMethod(AbilityMethodInfo abilityMethodInfo) {
    }

    protected void scanAbilityImpl(AbilityImplInfo abilityImplInfo) {
    }
}
