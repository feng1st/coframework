package io.codeone.framework.chain.context;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ContextTestTyped implements Typed {
    INT(Integer.class),
    STR(String.class),
    ;
    private final Class<?> type;
}