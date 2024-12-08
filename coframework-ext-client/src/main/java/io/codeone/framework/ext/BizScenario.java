package io.codeone.framework.ext;

import io.codeone.framework.ext.util.BizScenarioIterator;
import io.codeone.framework.ext.util.BizScenarioUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.Transient;
import java.util.Iterator;

/**
 * Represents an identifier for routing to a specific {@code Extension} implementation
 * of an {@code Extensible} interface.
 *
 * <p>An {@code Extensible} interface is an interface annotated with {@code Ability}
 * or {@code ExtensionPoint}, which defines a contract for extensible behavior.
 * An {@code Extension} implementation is a concrete implementation of an {@code
 * Extensible} interface, annotated with {@code Extension}, that specifies its identifier
 * as a coordinate using business identity ({@code bizId}) and scenario.
 *
 * <p>A call to an {@code Extensible} interface can automatically locate and execute
 * a specific {@code Extension} implementation based on the provided or contextual
 * {@code BizScenario}.
 *
 * <p>Examples:
 * <ul>
 *   <li>{@code @Ability
 *   interface Work { void execute(BizScenario bizScenario); }}
 *   <li>{@code @Extension(bizId = "full-time", scenarios = "weekday.monday")
 *   class WorkForFullTimeMonday implements Work {}}
 *   <li>{@code @Extension(bizId = "full-time", scenarios = "weekday")
 *   class WorkForFullTimeWeekday implements Work {}}
 *   <li>{@code @Extension(bizId = "outsourced", scenarios = "weekday.monday")
 *   class WorkForOutsourcedMonday implements Work {}}
 * </ul>
 *
 * <p>Routing examples:
 * <ul>
 *   <li>{@code work.execute(BizScenario.of("full-time", "weekday.monday"))} will
 *   route to {@code WorkForFullTimeMonday}.
 *   <li>{@code work.execute(BizScenario.of("full-time", "weekday.friday"))} will
 *   route to {@code WorkForFullTimeWeekday}, as the exact scenario match is not
 *   found, but its hierarchical traversal identifies {@code BizScenario.of("full-time",
 *   "weekday")}.
 *   <li>{@code work.execute(BizScenario.of("outsourced", "weekday.monday"))} will
 *   route to {@code WorkForOutsourcedMonday}.
 * </ul>
 *
 * <p>This class provides utilities for defining and manipulating a combination
 * of business identity and scenario. It supports hierarchical traversal of both
 * {@code bizId} and {@code scenario}, such as moving from specific levels (e.g.,
 * {@code "region.branch"}) to broader levels (e.g., {@code "region"} or the wildcard
 * {@code "*"}).
 */
@Getter
@EqualsAndHashCode
public class BizScenario implements BizScenarioParam, Iterable<BizScenario> {

    private String bizId = BizScenarioUtils.ANY;

    private String scenario = BizScenarioUtils.ANY;

    /**
     * Creates a new {@code BizScenario} with the specified business identity.
     *
     * @param bizId the business identity
     * @return a new {@code BizScenario} representing the business identity and
     * scenario
     */
    public static BizScenario ofBizId(String... bizId) {
        return of(BizScenarioUtils.join(bizId), BizScenarioUtils.ANY);
    }

    /**
     * Creates a new {@code BizScenario} with the specified scenario.
     *
     * @param scenario the scenario
     * @return a new {@code BizScenario} representing the business identity and
     * scenario
     */
    public static BizScenario ofScenario(String... scenario) {
        return of(BizScenarioUtils.ANY, BizScenarioUtils.join(scenario));
    }

    /**
     * Creates a new {@code BizScenario} with a business identity and scenario.
     *
     * @param bizId    a business identity
     * @param scenario a scenario
     * @return a new {@code BizScenario} representing the business identity and
     * scenario
     */
    public static BizScenario of(String bizId, String scenario) {
        BizScenario bizScenario = new BizScenario();
        bizScenario.setBizId(bizId);
        bizScenario.setScenario(scenario);
        return bizScenario;
    }

    /**
     * Creates a new {@code BizScenario} by replacing the business identity.
     *
     * @param bizId new business identity
     * @return a new {@code BizScenario} with the updated business identity and
     * the existing scenario
     */
    public BizScenario withBizId(String... bizId) {
        return of(BizScenarioUtils.join(bizId), this.scenario);
    }

    /**
     * Creates a new {@code BizScenario} by replacing the scenario.
     *
     * @param scenario new scenario
     * @return a new {@code BizScenario} with the existing business identity and
     * the updated scenario
     */
    public BizScenario withScenario(String... scenario) {
        return of(this.bizId, BizScenarioUtils.join(scenario));
    }

    /**
     * Creates a new {@code BizScenario} by prepending a new scenario level to the
     * existing scenario hierarchy.
     *
     * @param scenario the scenario level to prepend
     * @return a new {@code BizScenario} with the prepended scenario and the existing
     * business identity
     */
    public BizScenario prependScenario(String scenario) {
        return withScenario(scenario, this.scenario);
    }

    /**
     * Creates a new {@code BizScenario} by appending a new scenario level to the
     * existing scenario hierarchy.
     *
     * @param scenario the scenario level to append
     * @return a new {@code BizScenario} with the appended scenario and the existing
     * business identity
     */
    public BizScenario appendScenario(String scenario) {
        return of(this.scenario, scenario);
    }

    /**
     * Sets the business identity for this {@code BizScenario}.
     *
     * <p>The provided business identity is validated and normalized. If it is empty
     * or null, it is replaced with the wildcard {@code "*"}.
     *
     * @param bizId the business identity
     */
    public void setBizId(String bizId) {
        BizScenarioUtils.validate(bizId);
        this.bizId = BizScenarioUtils.emptyToAny(bizId);
    }

    /**
     * Sets the scenario for this {@code BizScenario}.
     *
     * <p>The provided scenario is validated and normalized. If it is empty or null,
     * it is replaced with the wildcard {@code "*"}.
     *
     * @param scenario the scenario
     */
    public void setScenario(String scenario) {
        BizScenarioUtils.validate(scenario);
        this.scenario = BizScenarioUtils.emptyToAny(scenario);
    }

    /**
     * Returns a string representation of the business identity and scenario.
     *
     * <p>The format of the string is {@code "bizId|scenario"}.
     *
     * @return a string representation of this {@code BizScenario}
     */
    @Override
    public String toString() {
        return bizId + "|" + scenario;
    }

    /**
     * Retrieves the current {@code BizScenario} instance.
     *
     * @return the current {@code BizScenario} instance
     */
    @Override
    @Transient
    public BizScenario getBizScenario() {
        return this;
    }

    /**
     * Returns an iterator to traverse hierarchical levels of both {@code bizId}
     * and {@code scenario} in this {@code BizScenario}.
     *
     * <p>The iteration starts from the most specific levels (e.g., {@code "region.branch"
     * or "weekday.monday"}) and moves to broader levels (e.g., {@code "region"
     * or "weekday"}), eventually reaching the wildcard {@code "*"}.
     *
     * @return an iterator for hierarchical traversal of this {@code BizScenario}
     */
    @Override
    public Iterator<BizScenario> iterator() {
        return new BizScenarioIterator(this);
    }
}
