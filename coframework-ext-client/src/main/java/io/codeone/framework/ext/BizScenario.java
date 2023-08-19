package io.codeone.framework.ext;

import io.codeone.framework.ext.session.ExtensionSession;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Iterator;

/**
 * {@code BizScenario} represents the business identity of a service caller, and
 * in which scenario the service call will be. When invoking an Extensible such
 * as an ability or an extension point, the framework uses these information to
 * locate a concrete Extension (a particular implementation of that Extensible
 * interface) to invoke.
 *
 * <p>Actually there are two {@code BizScenario} concepts in this process. One
 * is the {@code BizScenario} argument of the invocation, usually it can be
 * resolved from the arguments of the call, please refer to {@link Extensible}
 * and {@link ExtensionSession} for more information. And the other is the
 * "coordinate" of an {@link Extension} annotation, which will be matched with
 * the aforementioned argument to locate a particular Extension.
 *
 * <p>If the {@code bizId} and {@code scenario} attributes of the
 * {@code BizScenario} argument could not match with the "coordinate" of one
 * {@code Extension} exactly, the framework will try to broaden the
 * {@code scenario} then the {@code bizId} to find an {@code Extension}. For
 * example, for {@code ("BIZ1.TEAM_A", "holiday.new-year")} {@code BizScenario}
 * argument, the framework will try to match it with the following
 * {@code Extension} "coordinates" in turn:
 * <pre>
 * ("BIZ1.TEAM_A", "holiday.new-year")
 * ("BIZ1.TEAM_A", "holiday")
 * ("BIZ1.TEAM_A", "*")
 * ("BIZ1", "holiday.new-year")
 * ("BIZ1", "holiday")
 * ("BIZ1", "*")
 * ("*", "holiday.new-year")
 * ("*", "holiday")
 * ("*", "*")
 * </pre>
 * And an exception will be thrown if the framework could not find any
 * {@code Extension} for the specified {@code BizScenario} argument.
 *
 * @see Extensible
 * @see ExtensionSession
 */
@Getter
@EqualsAndHashCode
public class BizScenario implements BizScenarioParam, Iterable<BizScenario> {

    /**
     * The constant that represents either any {@link #bizId} or any
     * {@link #scenario}.
     */
    public static final String ANY = "*";

    private static final String CONNECTOR = "@";

    /**
     * The business identity hierarchy of a caller. Should be a "." separated
     * business identities and each identity should consist of alphabets,
     * numbers, "-" and "_" only, for example, "BIZ1.TEAM_A". Or {@link #ANY}
     * which represents everyone. The default value is {@code ANY}.
     */
    private String bizId = ANY;

    /**
     * The scenario hierarchy of the call. Should be a "." separated scenarios
     * and each scenario should consist of alphabets, numbers, "-" and "_" only,
     * for example, "holiday.new-year". Or {@link #ANY} which represents all
     * scenarios. The default value is {@code ANY}.
     */
    private String scenario = ANY;

    /**
     * Constructs a {@code BizScenario} from a business identity hierarchy, for
     * example, {@code ofBizId("BIZ1", "TEAM_A")}. Please refer to
     * {@link #bizId} for format and restriction. The scenario of the
     * constructed {@code BizScenario} is {@link #ANY}.
     *
     * @param bizId the sequence of the business identity hierarchy. Empty
     *              sequence represents {@code ANY}
     * @return the constructed {@code BizScenario}
     * @see #bizId
     */
    public static BizScenario ofBizId(String... bizId) {
        return of(joinValues(bizId), ANY);
    }

    /**
     * Constructs a {@code BizScenario} from a scenario hierarchy, for example,
     * {@code ofBizId("holiday", "new-year")}. Please refer to {@link #scenario}
     * for format and restriction. The business identity of the constructed
     * {@code BizScenario} is {@link #ANY}.
     *
     * @param scenario the sequence of the scenario hierarchy. Empty sequence
     *                 represents {@code ANY}
     * @return the constructed {@code BizScenario}
     * @see #scenario
     */
    public static BizScenario ofScenario(String... scenario) {
        return of(ANY, joinValues(scenario));
    }

    /**
     * Constructs a {@code BizScenario} from a business identity hierarchy and a
     * scenario hierarchy. Please refer to {@link #bizId} and {@link #scenario}
     * for format and restriction.
     *
     * @param bizId    the array of the business identity hierarchy. Null or
     *                 empty array represents {@link #ANY}
     * @param scenario the array of the scenario hierarchy. Null or empty array
     *                 represents {@code ANY}
     * @return the constructed {@code BizScenario}
     * @see #bizId
     * @see #scenario
     */
    public static BizScenario of(String[] bizId, String[] scenario) {
        return of(joinValues(bizId), joinValues(scenario));
    }

    /**
     * Constructs a {@code BizScenario} from a business identity hierarchy
     * (array) and a scenario hierarchy (string). Please refer to {@link #bizId}
     * and {@link #scenario} for format and restriction. This is a special
     * method that should only be used by the framework.
     *
     * @param bizId    the array of the business identity hierarchy. Null or
     *                 *                empty array represents {@link #ANY}
     * @param scenario the string of the scenario hierarchy
     * @return the constructed {@code BizScenario}
     * @see #bizId
     * @see #scenario
     */
    public static BizScenario of(String[] bizId, String scenario) {
        return of(joinValues(bizId), scenario);
    }

    /**
     * Constructs a {@code BizScenario} from a business identity hierarchy and a
     * scenario hierarchy. Please refer to {@link #bizId} and {@link #scenario}
     * for format and restriction.
     *
     * @param bizId    the business identity hierarchy
     * @param scenario the scenario hierarchy
     * @return the constructed {@code BizScenario}
     * @see #bizId
     * @see #scenario
     */
    public static BizScenario of(String bizId, String scenario) {
        BizScenario bizScenario = new BizScenario();
        bizScenario.setBizId(bizId);
        bizScenario.setScenario(scenario);
        return bizScenario;
    }

    /**
     * Parses a {@code BizScenario} from a code which is generated by
     * {@link #getCode()}.
     *
     * @param code the code which is generated by {@code getCode()}
     * @return the parsed {@code BizScenario}
     */
    public static BizScenario parse(String code) {
        String[] array = code.split(CONNECTOR);
        if (array.length != 2) {
            throw new IllegalArgumentException("Invalid bizScenario '" + code + "'");
        }
        return of(array[0], array[1]);
    }

    /**
     * Constructs another {@code BizScenario} from this instance, but with a new
     * business identity hierarchy. Please refer to {@link #bizId} for format
     * and restriction.
     *
     * @param bizId a new business identity hierarchy. Empty sequence represents
     *              {@code ANY}
     * @return a new {@code BizScenario} with a new business identity hierarchy
     * @see #bizId
     */
    public BizScenario withBizId(String... bizId) {
        return of(joinValues(bizId), this.scenario);
    }

    /**
     * Constructs another {@code BizScenario} from this instance, but with a new
     * scenario hierarchy. Please refer to {@link #scenario} for format and
     * restriction.
     *
     * @param scenario a new scenario hierarchy. Empty sequence represents
     *                 {@code ANY}
     * @return a new {@code BizScenario} with a new scenario hierarchy
     * @see #scenario
     */
    public BizScenario withScenario(String... scenario) {
        return of(this.bizId, joinValues(scenario));
    }

    /**
     * Sets the business identity hierarchy. Null or empty string is permitted
     * and will be regarded as {@link #ANY}. Please refer to {@link #bizId} for
     * format and restriction.
     *
     * @param bizId business identity hierarchy of this {@code BizScenario}
     * @see #bizId
     */
    public void setBizId(String bizId) {
        if (bizId == null || bizId.isEmpty()) {
            bizId = ANY;
        }
        if (!isValueValid(bizId)) {
            throw new IllegalArgumentException("Invalid bizId '" + bizId
                    + "', should be '*', or '.' separated alphabets, numbers, '-' and '_'.");
        }
        this.bizId = bizId;
    }

    /**
     * Sets the scenario hierarchy. Null or empty string is permitted and will
     * be regarded as {@link #ANY}. Please refer to {@link #scenario} for format
     * and restriction.
     *
     * @param scenario scenario hierarchy of this {@code BizScenario}
     * @see #scenario
     */
    public void setScenario(String scenario) {
        if (scenario == null || scenario.isEmpty()) {
            scenario = ANY;
        }
        if (!isValueValid(scenario)) {
            throw new IllegalArgumentException("Invalid scenario '" + scenario
                    + "', should be '*', or '.' separated alphabets, numbers, '-' and '_'.");
        }
        this.scenario = scenario;
    }

    /**
     * Returns the code that represents this {@code BizScenario}. And this code
     * can be parsed to a {@code BizScenario} instance again (by
     * {@link #parse(String)}).
     *
     * @return the code that represents this {@code BizScenario}
     */
    public String getCode() {
        return bizId + CONNECTOR + scenario;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BizScenario getBizScenario() {
        return this;
    }

    @Override
    public Iterator<BizScenario> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        return getCode();
    }

    private class Itr implements Iterator<BizScenario> {

        private String bizId;

        private String scenario;

        public Itr() {
            this.bizId = BizScenario.this.bizId;
            this.scenario = BizScenario.this.scenario;
        }

        @Override
        public boolean hasNext() {
            return hasNextValue(bizId) && hasNextValue(scenario);
        }

        @Override
        public BizScenario next() {
            BizScenario next = BizScenario.of(bizId, scenario);
            scenario = nextValue(scenario);
            if (!hasNextValue(scenario)) {
                bizId = nextValue(bizId);
                scenario = BizScenario.this.scenario;
            }
            return next;
        }
    }

    private static final char SEPARATOR_CHAR = '.';

    private static final String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

    private static boolean isValueValid(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (ANY.equals(value)) {
            return true;
        }
        int lastLen = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == SEPARATOR_CHAR) {
                if (lastLen == 0) {
                    return false;
                }
                lastLen = 0;
            } else if (c == '-'
                    || (c >= '0' && c <= '9')
                    || c == '_'
                    || (c >= 'A' && c <= 'Z')
                    || (c >= 'a' && c <= 'z')) {
                lastLen += 1;
            } else {
                return false;
            }
        }
        return lastLen > 0;
    }

    private static String joinValues(String[] values) {
        if (values == null || values.length == 0) {
            return ANY;
        }
        if (values.length == 1) {
            return values[0];
        }
        return String.join(SEPARATOR, values);
    }

    private static boolean hasNextValue(String value) {
        return value != null;
    }

    private static String nextValue(String value) {
        if (ANY.equals(value)) {
            return null;
        }
        int i = value.lastIndexOf(SEPARATOR_CHAR);
        if (i == -1) {
            return ANY;
        }
        return value.substring(0, i);
    }
}
