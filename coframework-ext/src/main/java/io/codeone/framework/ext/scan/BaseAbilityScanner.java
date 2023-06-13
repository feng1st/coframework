package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.ExtMethod;

import java.lang.reflect.Method;

public abstract class BaseAbilityScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        scanAbility(ability, extensibleClass);
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanAbilityMethod(ability, extMethod, extensibleClass, method);
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario workingBizScenario) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        ExtMethod extMethod = method.getAnnotation(ExtMethod.class);
        scanAbilityImpl(ability, extMethod, extensibleClass, method, implementingClass, workingBizScenario);
    }

    private Ability getAbility(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(Ability.class);
    }

    protected void scanAbility(Ability ability, Class<?> extensibleClass) {
    }

    protected void scanAbilityMethod(Ability ability, ExtMethod extMethod, Class<?> extensibleClass, Method method) {
    }

    protected void scanAbilityImpl(Ability ability, ExtMethod extMethod, Class<?> extensibleClass, Method method,
                                   Class<?> implementingClass, BizScenario workingBizScenario) {
    }
}
