package io.codeone.framework.ext.scan;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.ext.Description;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * Scanner for abilities. You can extend this class to for example store and
 * index these models on the server side.
 */
public abstract class BaseAbilityScanner extends BaseExtScanner {

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtensible(Class<?> extensibleClass) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        scanAbility(AbilityDef.of(
                extensibleClass.getAnnotation(Description.class), extensibleClass));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtensibleMethod(Class<?> extensibleClass, Method method) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        scanAbilityMethod(AbilityMethod.of(
                method.getAnnotation(Description.class), method.getAnnotation(Order.class), method));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scanExtension(Class<?> extensibleClass, Method method,
                              Class<?> implementingClass, BizScenario bizScenario) {
        if (!extensibleClass.isAnnotationPresent(Ability.class)) {
            return;
        }
        scanAbilityImpl(AbilityImpl.of(method, implementingClass, bizScenario));
    }

    /**
     * Lets you process the ability definition the framework found.
     *
     * @param abilityDef the ability definition the framework found
     */
    protected void scanAbility(AbilityDef abilityDef) {
    }

    /**
     * Lets you process the ability method definition the framework found.
     *
     * @param abilityMethod the ability method definition the framework found
     */
    protected void scanAbilityMethod(AbilityMethod abilityMethod) {
    }

    /**
     * Lets you process the ability method implementation the framework found.
     *
     * @param abilityImpl the ability method implementation the framework found
     */
    protected void scanAbilityImpl(AbilityImpl abilityImpl) {
    }
}
