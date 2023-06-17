package io.codeone.framework.logging;

public enum LogDelimiters {
    DEFAULT("||", "=>"),
    UNIQUER("|||", "==>"),
    SIMPLE(",,", "::"),
    ;

    private final String field;

    private final String kv;

    LogDelimiters(String field, String kv) {
        this.field = field;
        this.kv = kv;
    }

    public String getField() {
        return field;
    }

    public String getKv() {
        return kv;
    }
}
