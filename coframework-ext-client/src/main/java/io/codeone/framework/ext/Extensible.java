package io.codeone.framework.ext;

import io.codeone.framework.ext.session.ExtensionSession;

import java.lang.annotation.*;

/**
 * This annotation is used to mark an Extensible interface, indirectly. To be
 * specific, we use {@link Ability} and {@link ExtensionPoint} which are two
 * "direct" {@code Extensible} annotations to mark these interfaces.
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
 * <p>It is recommended to use {@code ExtensionSession} to eliminate code
 * intrusiveness of the {@code BizScenarioParam} parameters (for example, the
 * parameter in the {@code SaveAbility#save(BizScenarioParam)} method, please
 * refer to {@link ExtensionSession} for more information.
 *
 * @see Ability
 * @see Extension
 * @see ExtensionPoint
 * @see ExtensionSession
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Extensible {
}
