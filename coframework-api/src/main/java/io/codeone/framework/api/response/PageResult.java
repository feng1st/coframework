package io.codeone.framework.api.response;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> implements ApiResult<List<T>>, PageData<T> {

    private boolean success;

    private List<T> data;

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private Long totalCount;

    private String errorCode;

    private String errorMessage;

    public static <T> PageResult<T> success(PageData<T> pageData) {
        return success(pageData.getData(), pageData.getPageIndex(), pageData.getPageSize(), pageData.getTotalCount());
    }

    public static <T> PageResult<T> success(int pageIndex, int pageSize) {
        return success(null, pageIndex, pageSize, null);
    }

    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize) {
        return success(data, pageIndex, pageSize, null);
    }

    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize, Long totalCount) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        result.setTotalCount(totalCount);
        return result;
    }

    public static <T> PageResult<T> failure(String errorCode, String errorMessage) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.START_PAGE_INDEX);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, PageConstants.MIN_PAGE_SIZE);
    }
}
