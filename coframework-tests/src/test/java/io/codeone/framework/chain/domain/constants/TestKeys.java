package io.codeone.framework.chain.domain.constants;

import io.codeone.framework.chain.domain.model.User;
import io.codeone.framework.model.Key;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public enum TestKeys implements Key {
    USER_ID(Long.class),
    USER(User.class),
    COUNT(Integer.class),
    EXT1_PARAM(String.class),
    EXT1_RESULT(String.class),
    EXT2_PARAM(String.class),
    EXT2_RESULT(String.class),
    ASYNC_INDEX(Integer.class),
    ASYNC_SUM(Integer.class),
    ASYNC_LIST(List.class),
    ;

    private final Class<?> clazz;
}