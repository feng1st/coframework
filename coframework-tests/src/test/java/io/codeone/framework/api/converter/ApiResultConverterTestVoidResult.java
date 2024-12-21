package io.codeone.framework.api.converter;

import lombok.Data;

@Data
public class ApiResultConverterTestVoidResult<T> {

    private boolean succ;

    private T data;

    public static <T> ApiResultConverterTestVoidResult<T> succ(T data) {
        ApiResultConverterTestVoidResult<T> result = new ApiResultConverterTestVoidResult<>();
        result.setSucc(true);
        result.setData(data);
        return result;
    }
}
