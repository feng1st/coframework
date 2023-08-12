package io.codeone.framework.ext.util;

import io.codeone.framework.ext.Ability;
import io.codeone.framework.ext.ExtensionPoint;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utilities for extension model scanning.
 */
@UtilityClass
public class ScanModelUtils {

    /**
     * Return the unique key of a class.
     *
     * @param clazz the class of an extension model
     * @return the unique key of the class
     */
    public String getClassKey(Class<?> clazz) {
        return clazz.getName();
    }

    /**
     * Return the key of the declaring class of a method.
     *
     * @param method the method of an extension model
     * @return the key of the declaring class of a method
     */
    public String getClassKey(Method method) {
        return method.getDeclaringClass().getName();
    }

    /**
     * Returns the key of a method.
     *
     * @param method the method of an extension model
     * @return the key of a method
     */
    public String getMethodKey(Method method) {
        return method.getName()
                + Arrays.stream(method.getParameterTypes())
                .map(Class::getSimpleName)
                .collect(Collectors.joining(",", "(", ")"));
    }

    /**
     * Returns the name of an ability interface.
     *
     * @param ability         the annotation of that ability interface
     * @param extensibleClass the ability interface
     * @return the name of the ability interface
     */
    public String getName(Ability ability, Class<?> extensibleClass) {
        return ability.name().isEmpty() ? extensibleClass.getSimpleName() : ability.name();
    }

    /**
     * Returns the name of an ability method.
     *
     * @param abilityMethod the annotation of that ability method
     * @param method        the ability method
     * @return the name of the ability method
     */
    public String getName(Ability.Method abilityMethod, Method method) {
        return (abilityMethod == null || abilityMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : abilityMethod.name();
    }

    /**
     * Returns the description of an ability method.
     *
     * @param abilityMethod the annotation of that ability method
     * @return the description of the ability method
     */
    public String getDescription(Ability.Method abilityMethod) {
        return abilityMethod == null ? "" : abilityMethod.description();
    }

    /**
     * Returns the virtual order of an ability method.
     *
     * @param abilityMethod the annotation of that ability method
     * @return the virtual order of the ability method
     */
    public int getOrder(Ability.Method abilityMethod) {
        return abilityMethod == null ? -1 : abilityMethod.order();
    }

    /**
     * Returns the name of an extension point interface.
     *
     * @param extPt           the annotation of that extension point interface
     * @param extensibleClass the extension point interface
     * @return the name of the extension point interface
     */
    public String getName(ExtensionPoint extPt, Class<?> extensibleClass) {
        return extPt.name().isEmpty() ? extensibleClass.getSimpleName() : extPt.name();
    }

    /**
     * Returns the name of an extension point method.
     *
     * @param extPtMethod the annotation of that extension point method
     * @param method      the extension point method
     * @return the name of the extension point method
     */
    public String getName(ExtensionPoint.Method extPtMethod, Method method) {
        return (extPtMethod == null || extPtMethod.name().isEmpty())
                ? method.getDeclaringClass().getSimpleName() + "." + method.getName() : extPtMethod.name();
    }

    /**
     * Returns the description of an extension point method.
     *
     * @param extPtMethod the annotation of that extension point method
     * @return the description of the extension point method
     */
    public String getDescription(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? "" : extPtMethod.description();
    }

    /**
     * Returns the virtual order of an extension point method.
     *
     * @param extPtMethod the annotation of that extension point method
     * @return the virtual order of the extension point method
     */
    public int getOrder(ExtensionPoint.Method extPtMethod) {
        return extPtMethod == null ? -1 : extPtMethod.order();
    }
}
