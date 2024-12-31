package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiErrorCodeConverterTestVoidException extends RuntimeException {

    @Getter
    private String code;

    public ApiErrorCodeConverterTestVoidException(String code) {
        super();
        this.code = code;
    }

    public ApiErrorCodeConverterTestVoidException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
