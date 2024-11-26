package io.codeone.framework.api.parameter;

import io.codeone.framework.api.constant.PageConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for pagination parameters.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BasePageParam extends BaseParam {

    private int pageIndex = PageConstants.START_PAGE_INDEX;

    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

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

    /**
     * Calculates the offset based on the current page index and page size.
     *
     * @return the offset value
     */
    public long getOffset() {
        return ((long) this.pageIndex - PageConstants.START_PAGE_INDEX) * this.pageSize;
    }
}
