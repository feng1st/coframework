package io.codeone.framework.api.converter.support;

import io.codeone.framework.api.converter.ApiResultConverter;
import io.codeone.framework.api.response.ApiResult;
import io.codeone.framework.api.response.Page;
import io.codeone.framework.api.response.PageResult;
import io.codeone.framework.api.response.Result;
import org.springframework.stereotype.Component;

/**
 * Converts a {@link PageResult} to an {@link ApiResult}.
 */
@Component("coframeworkPageResultApiResultConverter")
public class PageResultApiResultConverter
        implements ApiResultConverter<PageResult<?>> {

    /**
     * Converts a {@code PageResult} to an {@code ApiResult}. Produces a successful
     * result if the {@code PageResult} indicates success; otherwise, produces a
     * failure result.
     *
     * @param source the {@code PageResult} to convert
     * @return an {@code ApiResult} representing the converted {@code PageResult}
     */
    @Override
    public ApiResult<?> convert(PageResult<?> source) {
        if (source.isSuccess()) {
            return Result.success(Page.of(source));
        } else {
            return Result.failure(source.getErrorCode(), source.getErrorMessage());
        }
    }
}
