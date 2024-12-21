package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiExceptionConverterTestLegacyException extends RuntimeException {

    @Getter
    private String code;

    public ApiExceptionConverterTestLegacyException(String code) {
        super();
        this.code = code;
    }

    public ApiExceptionConverterTestLegacyException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
