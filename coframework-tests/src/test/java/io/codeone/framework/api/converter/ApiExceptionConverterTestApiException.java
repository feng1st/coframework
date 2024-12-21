package io.codeone.framework.api.converter;

import io.codeone.framework.api.exception.BaseException;

public class ApiExceptionConverterTestApiException extends BaseException {

    public ApiExceptionConverterTestApiException(String code) {
        super(code);
    }

    public ApiExceptionConverterTestApiException(String code, String message) {
        super(code, message);
    }

    public ApiExceptionConverterTestApiException(String code, Throwable cause) {
        super(code, cause);
    }

    public ApiExceptionConverterTestApiException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
