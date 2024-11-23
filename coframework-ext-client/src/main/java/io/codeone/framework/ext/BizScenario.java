package io.codeone.framework.ext;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.Transient;
import java.util.Iterator;

@Getter
@EqualsAndHashCode
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

    public static BizScenario of(String[] bizId, String scenario) {
        return of(joinValues(bizId), scenario);
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

    public BizScenario withRootScenario(String... scenario) {
        if (scenario.length == 0) {
            return this;
        }
        if (scenario.length == 1
                && ANY.equals(scenario[0])) {
            return this;
        }
        if (ANY.equals(this.scenario)) {
            return withScenario(scenario);
        }
        String[] newScenario = new String[scenario.length + 1];
        System.arraycopy(scenario, 0, newScenario, 0, scenario.length);
        newScenario[scenario.length] = this.scenario;
        return of(this.bizId, joinValues(newScenario));
    }

    public BizScenario withLeafScenario(String... scenario) {
        if (scenario.length == 0) {
            return this;
        }
        if (scenario.length == 1
                && ANY.equals(scenario[0])) {
            return this;
        }
        if (ANY.equals(this.scenario)) {
            return withScenario(scenario);
        }
        String[] newScenario = new String[scenario.length + 1];
        newScenario[0] = this.scenario;
        System.arraycopy(scenario, 0, newScenario, 1, scenario.length);
        return of(this.bizId, joinValues(newScenario));
    }

    public void setBizId(String bizId) {
        if (bizId == null || bizId.isEmpty()) {
            bizId = ANY;
        }
        if (!isValueValid(bizId)) {
            throw new IllegalArgumentException("Invalid bizId '" + bizId
                    + "', should be '*', or '.' separated alphabets, numbers, '-' and '_'");
        }
        this.bizId = bizId;
    }

    public void setScenario(String scenario) {
        if (scenario == null || scenario.isEmpty()) {
            scenario = ANY;
        }
        if (!isValueValid(scenario)) {
            throw new IllegalArgumentException("Invalid scenario '" + scenario
                    + "', should be '*', or '.' separated alphabets, numbers, '-' and '_'");
        }
        this.scenario = scenario;
    }

    public String getCode() {
        return bizId + CONNECTOR + scenario;
    }

    @Override
    @Transient
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
