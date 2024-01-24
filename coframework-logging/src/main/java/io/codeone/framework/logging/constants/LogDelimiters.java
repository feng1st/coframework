package io.codeone.framework.logging.constants;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum LogDelimiters {
    DEFAULT("||", "=>"),
    UNIQUER("|||", "==>"),
    SIMPLE(",,", "::"),
    ;

    private final String field;

    private final String kv;
}
