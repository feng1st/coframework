package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiErrorConverterTestVoidException extends RuntimeException {

    @Getter
    private String code;

    public ApiErrorConverterTestVoidException(String code) {
        super();
        this.code = code;
    }

    public ApiErrorConverterTestVoidException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
