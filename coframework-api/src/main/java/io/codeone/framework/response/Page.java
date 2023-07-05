package io.codeone.framework.response;

import io.codeone.framework.util.PageConstants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * A container for paged result.
 * <p>
 * {@code Page} alone does not indicate the successfulness, code and message of
 * a request. To do that, use {@code Result<Page>} instead.
 *
 * @param <T> Type of the contained data.
 */
@Data
@Accessors(chain = true)
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
    private long totalCount = 0L;

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

    /**
     * Sets current page of the returned data. It should start from 1.
     */
    public Page<T> setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    /**
     * Sets the size of records of one page. It can't be zero or negative.
     */
    public Page<T> setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
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
        return (long) pageSize * pageIndex < totalCount;
    }
}
