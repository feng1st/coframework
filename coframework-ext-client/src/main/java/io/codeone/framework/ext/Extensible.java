package io.codeone.framework.ext;

import java.lang.annotation.*;

/**
 * This annotation is used to mark an Extensible interface, indirectly. To be
 * specific, we use {@link Ability} and {@link ExtensionPoint} which are two
 * "direct" {@code Extension} annotations to mark these interfaces.
 *
 * <p>An Extensible is an interface that can have multiple implementations
 * (which called Extensions) for different business identities and scenarios,
 * and all these Extensions can be autowired by this one Extensible interface.
 * Any call to this Extensible will be routed to a specific Extension by the
 * {@link BizScenario} instance in the parameters or in the context. For
 * example:
 * <pre>{@code
 * // The Extensible interface
 * @Ability
 * public interface SaveAbility {
 *     void save(BizScenarioParam param);
 * }
 *
 * // The Extension for "BIZ1"
 * @Extension(bizId = "BIZ1")
 * public class Biz1SaveAbility implements SaveAbility {
 *     @Override
 *     public void save(BizScenarioParam param) {
 *     }
 * }
 *
 * @Service
 * public class SaveService {
 *     // Autowired by the Extensible interface
 *     @Resource
 *     private SaveAbility saveAbility;
 *
 *     public void save(SaveParam param) {
 *         // (SaveParam is a subclass of BizScenarioParam)
 *         // Will route to the Biz1SaveAbility Extension if
 *         // param.getBizScenario() is (bizId = "BIZ1")
 *         saveAbility.save(param);
 *     }
 * }
 * }</pre>
 *
 * @see Ability
 * @see Extension
 * @see ExtensionPoint
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Extensible {
}
