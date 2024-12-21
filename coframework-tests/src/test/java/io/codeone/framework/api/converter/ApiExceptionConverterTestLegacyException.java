package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiExceptionConverterTestLegacyException extends RuntimeException {

    @Getter
    private String code;

    public ApiExceptionConverterTestLegacyException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
