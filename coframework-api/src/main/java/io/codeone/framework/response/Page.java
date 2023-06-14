package io.codeone.framework.response;

import io.codeone.framework.util.PageConstants;

import java.util.List;
import java.util.Objects;

public class Page<T> {

    private List<T> data;

    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private long totalCount = 0;

    public static <T> Page<T> empty(int pageIndex, int pageSize) {
        return page(null, pageIndex, pageSize);
    }

    public static <T> Page<T> page(List<T> data, int pageIndex, int pageSize) {
        return page(data, pageIndex, pageSize, 0L);
    }

    public static <T> Page<T> page(List<T> data, int pageIndex, int pageSize, long totalCount) {
        return new Page<T>()
                .setData(data)
                .setPageIndex(pageIndex)
                .setPageSize(pageSize)
                .setTotalCount(totalCount);
    }

    public List<T> getData() {
        return data;
    }

    public Page<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public Page<T> setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Page<T> setPageSize(int pageSize) {
        this.pageSize = Math.max(pageIndex, 1);
        return this;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Page<T> setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    public boolean getHasMore() {
        return (long) pageIndex * pageSize < totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Page<?> page = (Page<?>) o;
        return pageIndex == page.pageIndex
                && pageSize == page.pageSize
                && totalCount == page.totalCount
                && Objects.equals(data, page.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, pageIndex, pageSize, totalCount);
    }

    @Override
    public String toString() {
        return "Page{" +
                "data=" + data +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
