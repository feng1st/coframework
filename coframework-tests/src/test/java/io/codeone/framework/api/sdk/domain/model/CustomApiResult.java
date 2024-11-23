package io.codeone.framework.api.sdk.domain.model;

import lombok.Data;

@Data
public class CustomApiResult<T> {

    private boolean success;

    private T data;

    private String code;

    private String msg;
}
