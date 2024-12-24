package io.codeone.framework.api.parameter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BizPageParamTest {

    @Test
    public void setBizIdThenScenario() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        Assertions.assertNull(param.getBizId());
        Assertions.assertNull(param.getScenario());
        Assertions.assertNull(param.getBizScenario());
        param.setBizId("foo");
        param.setScenario("s");
        Assertions.assertEquals("foo", param.getBizId());
        Assertions.assertEquals("s", param.getScenario());
        Assertions.assertEquals("foo|s", param.getBizScenario().toString());
    }

    @Test
    public void setScenarioThenBizId() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        param.setScenario("s");
        param.setBizId("foo");
        Assertions.assertEquals("foo", param.getBizId());
        Assertions.assertEquals("s", param.getScenario());
        Assertions.assertEquals("foo|s", param.getBizScenario().toString());
    }


    @Test
    public void defaultValue() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(20, param.getPageSize());
    }

    @Test
    public void invalidValue() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        param.setPageIndex(0);
        param.setPageSize(0);
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(1, param.getPageSize());
    }

    @Test
    public void firstPage() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        param.setPageIndex(1);
        param.setPageSize(10);
        Assertions.assertEquals(1, param.getPageIndex());
        Assertions.assertEquals(10, param.getPageSize());
        Assertions.assertEquals(0, param.getOffset());
    }

    @Test
    public void fifthPage() {
        BizPageParamTestParam param = new BizPageParamTestParam();
        param.setPageIndex(5);
        param.setPageSize(10);
        Assertions.assertEquals(5, param.getPageIndex());
        Assertions.assertEquals(10, param.getPageSize());
        Assertions.assertEquals(40, param.getOffset());
    }
}