package io.codeone.framework.logging;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LogDelimiters {
    DEFAULT("||", "=>"),
    UNIQUER("|||", "==>"),
    SIMPLE(",,", "::"),
    ;

    private final String field;

    private final String kv;
}
