package io.codeone.framework.api.response;

public interface ApiResult<T> {

    boolean isSuccess();

    T getData();

    String getErrorCode();

    String getErrorMessage();
}
