package io.codeone.framework.api.response;

import java.util.List;

/**
 * Represents a paginated data set.
 *
 * @param <T> the type of data in the paginated set
 */
public interface PageData<T> {

    /**
     * Retrieves the list of data items for the current page.
     *
     * @return the list of data items
     */
    List<T> getData();

    /**
     * Retrieves the current page index.
     *
     * @return the current page index
     */
    int getPageIndex();

    /**
     * Retrieves the current page size.
     *
     * @return the current page size
     */
    int getPageSize();

    /**
     * Retrieves the total number of items available.
     *
     * @return the total count of items, or null if unknown
     */
    Long getTotalCount();

    /**
     * Checks if there are more pages available after the current page.
     *
     * @return true if more pages are available, false otherwise
     */
    default Boolean isHasMore() {
        if (getTotalCount() == null) {
            return null;
        }
        return (long) getPageSize() * getPageIndex() < getTotalCount();
    }
}
