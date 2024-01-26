package io.codeone.framework.chain.context;

public interface Typed {

    <T> Class<T> getType();

    default <T> T cast(Object obj) {
        return this.<T>getType().cast(obj);
    }
}
