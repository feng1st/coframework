package io.codeone.framework.chain.domain.constants;

import io.codeone.framework.chain.constants.Key;
import io.codeone.framework.chain.domain.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TestKey implements Key {
    USER_ID(Long.class),
    USER(User.class),
    COUNT(Integer.class),
    EXT1_PARAM(String.class),
    EXT1_RESULT(String.class),
    EXT2_PARAM(String.class),
    EXT2_RESULT(String.class),
    ;

    private final Class<?> clazz;
}
