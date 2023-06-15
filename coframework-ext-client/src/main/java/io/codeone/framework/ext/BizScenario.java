package io.codeone.framework.ext;

import java.util.Iterator;
import java.util.Objects;

public class BizScenario implements BizScenarioParam, Iterable<BizScenario> {

    public static final String ANY = "*";

    private static final String CONNECTOR = "@";

    private String bizId = ANY;

    private String scenario = ANY;

    public static BizScenario ofBizId(String... bizId) {
        return of(joinValues(bizId), ANY);
    }

    public static BizScenario ofScenario(String... scenario) {
        return of(ANY, joinValues(scenario));
    }

    public static BizScenario of(String[] bizId, String[] scenario) {
        return of(joinValues(bizId), joinValues(scenario));
    }

    public static BizScenario of(String bizId, String scenario) {
        BizScenario bizScenario = new BizScenario();
        bizScenario.setBizId(bizId);
        bizScenario.setScenario(scenario);
        return bizScenario;
    }

    public static BizScenario parse(String code) {
        String[] array = code.split(CONNECTOR);
        if (array.length != 2) {
            throw new IllegalArgumentException("Invalid bizScenario '" + code + "'");
        }
        return of(array[0], array[1]);
    }

    public BizScenario withBizId(String... bizId) {
        return of(joinValues(bizId), this.scenario);
    }

    public BizScenario withScenario(String... scenario) {
        return of(this.bizId, joinValues(scenario));
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        if (!isValueValid(bizId)) {
            throw new IllegalArgumentException("Invalid bizId '" + bizId + "', should be '*' (which means ANY), or "
                    + "'.' separated keywords which are composed of only alphabets, numbers, '-' and '_'.");
        }
        this.bizId = bizId;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        if (!isValueValid(scenario)) {
            throw new IllegalArgumentException("Invalid scenario '" + scenario + "', should be '*' (which means ANY), "
                    + "or '.' separated keywords which are composed of only alphabets, numbers, '-' and '_'.");
        }
        this.scenario = scenario;
    }

    public String getCode() {
        return bizId + CONNECTOR + scenario;
    }

    @Override
    public BizScenario getBizScenario() {
        return this;
    }

    @Override
    public Iterator<BizScenario> iterator() {
        return new Itr();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BizScenario that = (BizScenario) o;
        return Objects.equals(bizId, that.bizId) && Objects.equals(scenario, that.scenario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bizId, scenario);
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
