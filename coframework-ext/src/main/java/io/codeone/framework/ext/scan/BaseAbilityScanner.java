package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;

import java.lang.reflect.Method;

public abstract class BaseAbilityScanner extends BaseExtScanner {

    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        scanAbility(extensibleClass, ability);
    }

    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        scanAbilityMethod(extensibleClass, ability, method);
    }

    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
        Ability ability = getAbility(extensibleClass);
        if (ability == null) {
            return;
        }
        scanAbilityImpl(extensibleClass, ability, method, methodDeclaringClass, methodBizScenario);
    }

    private Ability getAbility(Class<?> extensibleClass) {
        return extensibleClass.getAnnotation(Ability.class);
    }

    protected void scanAbility(Class<?> extensibleClass, Ability ability) {
    }

    protected void scanAbilityMethod(Class<?> extensibleClass, Ability ability, Method method) {
    }

    protected void scanAbilityImpl(Class<?> extensibleClass, Ability ability, Method method,
                                   Class<?> methodDeclaringClass, BizScenario methodBizScenario) {
    }
}
