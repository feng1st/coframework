package io.codeone.framework.request;

import io.codeone.framework.util.PageConstants;

import java.util.Objects;

/**
 * Base class of all paginating Requests.
 */
public abstract class PageRequest extends BaseRequest {

    /**
     * Current page.
     */
    private int pageIndex = PageConstants.DEFAULT_PAGE_INDEX_START;

    /**
     * The size of one page.
     */
    private int pageSize = PageConstants.DEFAULT_PAGE_SIZE;

    public int getPageIndex() {
        return pageIndex;
    }

    public PageRequest setPageIndex(int pageIndex) {
        this.pageIndex = Math.max(pageIndex, PageConstants.DEFAULT_PAGE_INDEX_START);
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageRequest setPageSize(int pageSize) {
        this.pageSize = Math.max(pageSize, 1);
        return this;
    }

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
