package io.codeone.framework.response;

import java.util.Objects;

public class Result<T> {

    private boolean success;

    private T data;

    private String errorCode;

    private String errorMessage;

    public static <T> Result<T> success() {
        return new Result<T>()
                .setSuccess(true);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>()
                .setSuccess(true)
                .setData(data);
    }

    public static <T> Result<T> fail(String errorCode, String errorMessage) {
        return new Result<T>()
                .setSuccess(false)
                .setErrorCode(errorCode)
                .setErrorMessage(errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Result<T> setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Result<T> setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Result<?> result = (Result<?>) o;
        return success == result.success
                && Objects.equals(data, result.data)
                && Objects.equals(errorCode, result.errorCode)
                && Objects.equals(errorMessage, result.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, data, errorCode, errorMessage);
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
