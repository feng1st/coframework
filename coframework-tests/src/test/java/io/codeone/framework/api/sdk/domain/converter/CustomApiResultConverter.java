package io.codeone.framework.api.sdk.domain.converter;

import io.codeone.framework.api.converter.ApiResultConverter;
import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.sdk.domain.model.CustomApiResult;
import org.springframework.stereotype.Component;

@Component
public class CustomApiResultConverter<T> implements ApiResultConverter<CustomApiResult<T>> {

    @Override
    public ApiResult<T> convert(CustomApiResult<T> source) {
        return new ApiResult<T>() {
            @Override
            public boolean isSuccess() {
                return source.isSuccess();
            }

            @Override
            public T getData() {
                return source.getData();
            }

            @Override
            public String getErrorCode() {
                return source.getCode();
            }

            @Override
            public String getErrorMessage() {
                return source.getMsg();
            }
        };
    }
}
