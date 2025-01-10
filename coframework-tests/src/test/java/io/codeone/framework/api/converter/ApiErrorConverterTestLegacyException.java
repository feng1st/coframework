package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiErrorConverterTestLegacyException extends RuntimeException {

    @Getter
    private String code;

    public ApiErrorConverterTestLegacyException(String code) {
        super();
        this.code = code;
    }

    public ApiErrorConverterTestLegacyException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
