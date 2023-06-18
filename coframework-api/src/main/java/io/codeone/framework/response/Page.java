package io.codeone.framework.response;

import io.codeone.framework.util.PageConstants;

import java.util.List;
import java.util.Objects;

/**
 * A container for paged result.
 * <p>
 * {@code Page} alone does not indicate the successfulness, code and message of
 * a request. To do that, use {@code Result<Page>} instead.
 *
 * @param <T> Type of the contained data.
 */
public class Page<T> {

    /**
     * The contained data.
     */
    private List<T> data;

    /**
     * Current page of the returned data. It should start from 1. Normally it
     * should be the same as the request.
     */
    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    /**
     * The size of records one page has. It can't be zero or negative. Normally
     * it should be the same as the request.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    /**
     * The total number of all records. It may be zero if the total number is
     * impossible or expensive to calculate, e.g. an endless feed.
     */
    private long totalCount = 0;

    /**
     * Constructs a result with no data.
     */
    public static <T> Page<T> empty(int pageIndex, int pageSize) {
        return page(null, pageIndex, pageSize);
    }

    /**
     * Constructs a result with data, pageIndex and pageSize, but no
     * totalCount.
     */
    public static <T> Page<T> page(List<T> data, int pageIndex, int pageSize) {
        return page(data, pageIndex, pageSize, 0L);
    }

    /**
     * Constructs a result with data, pageIndex, pageSize, and a valid
     * totalCount.
     */
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

    /**
     * Sets current page of the returned data. It should start from 1.
     */
    public Page<T> setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the size of records of one page. It can't be zero or negative.
     */
    public Page<T> setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Page<T> setTotalCount(long totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    /**
     * Returns true if there is no data, otherwise false.
     */
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    /**
     * Returns true if there is more data, otherwise false.
     * <p>
     * Notice that this result is incorrect if the totalCount is omitted and
     * has the default value 0.
     */
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
