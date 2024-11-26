package io.codeone.framework.api.response;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;

import java.util.List;

/**
 * Represents a general paginated data set.
 *
 * <p>The standard paginated result format is {@code Result<Page<T>>}, which includes
 * pagination information as part of the data.
 *
 * @param <T> the type of data in the paginated set
 */
@Data
public class Page<T> implements PageData<T> {

    private List<T> data;

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private Long totalCount;

    /**
     * Creates a {@code Page} instance from the given {@link PageData}.
     *
     * @param pageData the page data to use
     * @param <T>      the type of the data items
     * @return a new {@code Page} instance
     */
    public static <T> Page<T> of(PageData<T> pageData) {
        return of(pageData.getData(), pageData.getPageIndex(), pageData.getPageSize(), pageData.getTotalCount());
    }

    /**
     * Creates an empty {@code Page} instance with the specified page index and size.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new {@code Page} instance
     */
    public static <T> Page<T> of(int pageIndex, int pageSize) {
        return of(null, pageIndex, pageSize, null);
    }

    /**
     * Creates a {@code Page} instance with the specified data, page index, and size.
     *
     * @param data      the list of data items
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new {@code Page} instance
     */
    public static <T> Page<T> of(List<T> data, int pageIndex, int pageSize) {
        return of(data, pageIndex, pageSize, null);
    }

    /**
     * Creates a {@code Page} instance with the specified data, page index, size, and total count.
     *
     * @param data       the list of data items
     * @param pageIndex  the page index
     * @param pageSize   the page size
     * @param totalCount the total number of items available
     * @param <T>        the type of the data items
     * @return a new {@code Page} instance
     */
    public static <T> Page<T> of(List<T> data, int pageIndex, int pageSize, Long totalCount) {
        Page<T> page = new Page<>();
        page.setData(data);
        page.setPageIndex(pageIndex);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        return page;
    }

    /**
     * Sets the page index, ensuring it is not below the starting page index.
     *
     * @param pageIndex the page index to set
     */
    public void setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.START_PAGE_INDEX);
    }

    /**
     * Sets the page size, ensuring it is not below the minimum page size.
     *
     * @param pageSize the page size to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, PageConstants.MIN_PAGE_SIZE);
    }
}