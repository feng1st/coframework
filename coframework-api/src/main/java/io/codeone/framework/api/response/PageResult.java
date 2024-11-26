package io.codeone.framework.api.response;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;

import java.util.List;

/**
 * Represents a paginated result that includes metadata for pagination.
 *
 * @param <T> the type of data in the paginated result
 */
@Data
public class PageResult<T> implements ApiResult<List<T>>, PageData<T> {

    private boolean success;

    private List<T> data;

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    private Long totalCount;

    private String errorCode;

    private String errorMessage;

    /**
     * Creates a successful paginated result from the given {@link PageData}.
     *
     * @param pageData the page data to use
     * @param <T>      the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(PageData<T> pageData) {
        return success(pageData.getData(), pageData.getPageIndex(), pageData.getPageSize(), pageData.getTotalCount());
    }

    /**
     * Creates an empty successful paginated result with the specified page index and size.
     *
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(int pageIndex, int pageSize) {
        return success(null, pageIndex, pageSize, null);
    }

    /**
     * Creates a successful paginated result with the specified data, page index, and size.
     *
     * @param data      the list of data items
     * @param pageIndex the page index
     * @param pageSize  the page size
     * @param <T>       the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize) {
        return success(data, pageIndex, pageSize, null);
    }

    /**
     * Creates a successful paginated result with the specified data, page index, size, and total count.
     *
     * @param data       the list of data items
     * @param pageIndex  the page index
     * @param pageSize   the page size
     * @param totalCount the total number of items available
     * @param <T>        the type of the data items
     * @return a new successful {@code PageResult} instance
     */
    public static <T> PageResult<T> success(List<T> data, int pageIndex, int pageSize, Long totalCount) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(true);
        result.setData(data);
        result.setPageIndex(pageIndex);
        result.setPageSize(pageSize);
        result.setTotalCount(totalCount);
        return result;
    }

    /**
     * Creates a failure paginated result with an error code and message.
     *
     * @param errorCode    the error code
     * @param errorMessage the error message
     * @param <T>          the type of the data items
     * @return a new failure {@code PageResult} instance
     */
    public static <T> PageResult<T> failure(String errorCode, String errorMessage) {
        PageResult<T> result = new PageResult<>();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
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
