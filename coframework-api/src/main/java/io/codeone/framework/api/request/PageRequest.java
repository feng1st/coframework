package io.codeone.framework.api.request;

import io.codeone.framework.api.util.PageConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Base class of all paged requests. It is also a {@link BaseRequest}.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public abstract class PageRequest extends BaseRequest {

    /**
     * Current page of this request. It should start from 1.
     */
    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    /**
     * The number of records in one page. It must be a positive integer.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    /**
     * Sets current page of this request. It should start from 1.
     *
     * @param pageIndex current page of this request
     * @return itself (chaining)
     */
    public PageRequest setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    /**
     * Sets the number of records in one page. It must be a positive integer.
     *
     * @param pageSize the number of records in one page
     * @return itself (chaining)
     */
    public PageRequest setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

    /**
     * Returns the calculated starting position, that is the index of the first
     * record requested.
     *
     * @return the calculated starting position
     */
    public long getStartPos() {
        return ((long) pageIndex - PageConstants.DEFAULT_PAGE_INDEX_START) * pageSize;
    }
}
