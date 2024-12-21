package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiExceptionConverterTestVoidException extends RuntimeException {

    @Getter
    private String code;

    public ApiExceptionConverterTestVoidException(String code) {
        super();
        this.code = code;
    }

    public ApiExceptionConverterTestVoidException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
