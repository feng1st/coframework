package io.codeone.framework.api.response;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> implements PageData<T> {

    private List<T> data;

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private Long totalCount;

    public static <T> Page<T> of(PageData<T> pageData) {
        return of(pageData.getData(), pageData.getPageIndex(), pageData.getPageSize(), pageData.getTotalCount());
    }

    public static <T> Page<T> of(int pageIndex, int pageSize) {
        return of(null, pageIndex, pageSize, null);
    }

    public static <T> Page<T> of(List<T> data, int pageIndex, int pageSize) {
        return of(data, pageIndex, pageSize, null);
    }

    public static <T> Page<T> of(List<T> data, int pageIndex, int pageSize, Long totalCount) {
        Page<T> page = new Page<>();
        page.setData(data);
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        return page;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.START_PAGE_INDEX);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, PageConstants.MIN_PAGE_SIZE);
    }
}
