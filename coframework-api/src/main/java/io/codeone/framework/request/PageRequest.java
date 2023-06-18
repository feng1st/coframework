package io.codeone.framework.request;

import io.codeone.framework.util.PageConstants;

import java.util.Objects;

/**
 * Base class of all paged requests.
 */
public abstract class PageRequest extends BaseRequest {

    /**
     * Current page of the request. It should start from 1.
     */
    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    /**
     * The size of records in one page. It can't be zero or negative.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    public int getPageIndex() {
        return pageIndex;
    }

    /**
     * Sets current page of the request. It should start from 1.
     */
    public PageRequest setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    public int getPageSize() {
        return pageSize;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PageRequest that = (PageRequest) o;
        return pageIndex == that.pageIndex && pageSize == that.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pageIndex, pageSize);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                "} " + super.toString();
    }
}
