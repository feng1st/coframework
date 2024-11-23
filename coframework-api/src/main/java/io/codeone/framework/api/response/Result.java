package io.codeone.framework.api.response;

import lombok.Data;

@Data
public class Result<T> implements ApiResult<T> {

    private boolean success;

    private T data;

    private String errorCode;

    private String errorMessage;

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failure(String errorCode, String errorMessage) {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }
}
