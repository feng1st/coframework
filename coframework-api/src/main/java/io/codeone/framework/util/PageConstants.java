package io.codeone.framework.util;

import io.codeone.framework.request.PageRequest;
import io.codeone.framework.response.Page;

/**
 * Constants used by {@link PageRequest} and {@link Page}.
 */
public interface PageConstants {

    /**
     * The default value of starting page index.
     */
    int DEFAULT_PAGE_INDEX_START = 1;

    /**
     * The default value of number of records per page.
     */
    int DEFAULT_PAGE_SIZE = 20;
}
