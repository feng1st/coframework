package io.codeone.framework.api.converter;

import lombok.Getter;

public class ApiErrorCodeConverterTestLegacyException extends RuntimeException {

    @Getter
    private String code;

    public ApiErrorCodeConverterTestLegacyException(String code) {
        super();
        this.code = code;
    }

    public ApiErrorCodeConverterTestLegacyException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
