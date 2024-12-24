package io.codeone.framework.api.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

class PageResultTest {

    @Test
    public void invalidPageIndexSize() {
        PageResult<Object> result = PageResult.success(0, 0);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(1, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertNull(result.isHasMore());
    }

    @Test
    public void successPageData() {
        PageData<Object> pageData = Page.of(Arrays.asList(1, 2, 3), 5, 3, 100L);
        PageResult<Object> result = PageResult.success(pageData);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(3, result.getPageSize());
        Assertions.assertEquals(100L, result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertTrue(result.isHasMore());
    }

    @Test
    public void successPageIndexSize() {
        PageResult<Object> result = PageResult.success(5, 10);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(10, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertNull(result.isHasMore());
    }

    @Test
    public void successDataPageIndexSize() {
        PageResult<Object> result = PageResult.success(Arrays.asList(1, 2, 3), 5, 10);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(10, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertNull(result.isHasMore());
    }

    @Test
    public void successDataPageIndexSizeTotalCount() {
        PageResult<Object> result = PageResult.success(Arrays.asList(1, 2, 3), 5, 3, 100L);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(3, result.getPageSize());
        Assertions.assertEquals(100L, result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertTrue(result.isHasMore());
    }

    @Test
    public void failure() {
        PageResult<Object> result = PageResult.failure("CODE", "Message");
        Assertions.assertFalse(result.isSuccess());
        Assertions.assertNull(result.getData());
        Assertions.assertEquals(1, result.getPageIndex());
        Assertions.assertEquals(20, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertEquals("CODE", result.getErrorCode());
        Assertions.assertEquals("Message", result.getErrorMessage());
    }

    @Test
    public void isEmpty() {
        PageResult<Object> result = PageResult.success(Collections.emptyList(), 5, 3);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Collections.emptyList(), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(3, result.getPageSize());
        Assertions.assertNull(result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertNull(result.isHasMore());
    }

    @Test
    public void hasMore() {
        PageResult<Object> result = PageResult.success(Arrays.asList(1, 2, 3), 5, 3, 16L);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(3, result.getPageSize());
        Assertions.assertEquals(16L, result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertTrue(result.isHasMore());
    }

    @Test
    public void hasNoMore() {
        PageResult<Object> result = PageResult.success(Arrays.asList(1, 2, 3), 5, 3, 15L);
        Assertions.assertTrue(result.isSuccess());
        Assertions.assertEquals(Arrays.asList(1, 2, 3), result.getData());
        Assertions.assertEquals(5, result.getPageIndex());
        Assertions.assertEquals(3, result.getPageSize());
        Assertions.assertEquals(15L, result.getTotalCount());
        Assertions.assertNull(result.getErrorCode());
        Assertions.assertNull(result.getErrorMessage());
        Assertions.assertFalse(result.isHasMore());
    }
}