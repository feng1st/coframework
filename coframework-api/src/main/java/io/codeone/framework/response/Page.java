package io.codeone.framework.response;

import io.codeone.framework.util.PageConstants;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * A container for paged data.
 *
 * <p>{@code Page} alone does not indicate the successfulness, code and message
 * of a request. To do that, please use {@link Result}{@code <Page>} instead.
 *
 * @param <T> the type of the contained data
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
     * The number of records one page has. It must be a positive integer.
     * Normally it should be the same as the request.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    /**
     * The total number of all records. It may be zero if the total number is
     * impossible or expensive to be calculated, for example, an endless feeds.
     */
    private long totalCount = 0L;

    /**
     * Constructs an empty page (containing no data).
     *
     * @param pageIndex current page of the returned data
     * @param pageSize  the number of records one page has
     * @param <T>       the type of the contained data
     * @return the empty page
     */
    public static <T> Page<T> empty(int pageIndex, int pageSize) {
        return page(null, pageIndex, pageSize);
    }

    /**
     * Constructs a page with data and paging information, but does not give the
     * total number of records.
     *
     * @param data      the contained data, should be a list
     * @param pageIndex current page of the returned data
     * @param pageSize  the number of records one page has
     * @param <T>       the type of the contained data
     * @return the constructed page
     */
    public static <T> Page<T> page(List<T> data, int pageIndex, int pageSize) {
        return page(data, pageIndex, pageSize, 0L);
    }

    /**
     * Constructs a page with data, paging information, and the total number of
     * all records.
     *
     * @param data       the contained data, should be a list
     * @param pageIndex  current page of the returned data
     * @param pageSize   the number of records one page has
     * @param totalCount the total number of all records
     * @param <T>        the type of the contained data
     * @return the constructed page
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
     *
     * @param pageIndex current page of the returned data
     * @return itself (chaining)
     */
    public Page<T> setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    /**
     * Sets the number of records in one page. It must be a positive integer.
     *
     * @param pageSize the number of records in one page
     * @return itself (chaining)
     */
    public Page<T> setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

    /**
     * Returns {@code true} if it contains no data, otherwise {@code false}.
     *
     * @return {@code true} if it contains no data, otherwise {@code false}
     */
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    /**
     * Returns {@code true} if there is more page, otherwise {@code false}.
     *
     * <p>Please note that this result is incorrect if the {@code totalCount} is
     * omitted and has the default value 0.
     *
     * @return {@code true} if there is more page, otherwise {@code false}
     */
    public boolean getHasMore() {
        return (long) pageSize * pageIndex < totalCount;
    }
}
