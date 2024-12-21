package io.codeone.framework.api.converter;

import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Component;

@Component
public class ApiResultConverterTestLegacyResultConverter<T>
        implements ApiResultConverter<ApiResultConverterTestLegacyResult<T>> {

    @Override
    public ApiResult<T> convert(ApiResultConverterTestLegacyResult<T> source) {
        return source.isSucc()
                ? Result.success(source.getData())
                : Result.failure(source.getErrCode(), source.getErrMsg());
    }
}
