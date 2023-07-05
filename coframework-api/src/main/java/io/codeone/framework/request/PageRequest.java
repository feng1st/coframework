package io.codeone.framework.request;

import io.codeone.framework.util.PageConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Base class of all paged requests.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public abstract class PageRequest extends BaseRequest {

    /**
     * Current page of the request. It should start from 1.
     */
    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    /**
     * The size of records in one page. It can't be zero or negative.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    /**
     * Sets current page of the request. It should start from 1.
     */
    public PageRequest setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    /**
     * Sets the size of records in one page. It can't be zero or negative.
     */
    public PageRequest setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

    /**
     * Returns the calculated starting position, i.e. the index of the first
     * record that requested.
     */
    public long getStartPos() {
        return ((long) pageIndex - PageConstants.DEFAULT_PAGE_INDEX_START) * pageSize;
    }
}
