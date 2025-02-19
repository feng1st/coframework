package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.shared.BaseLoggingTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


@Slf4j
class LogTest extends BaseLoggingTest {

    @Test
    void setLogger() {
        Log.newLog()
                .setLogger(log)
                .log();
        assertLog("com.alibaba.framework3.logging.LogTest",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
    }

    @Test
    void setLoggerNameEmpty() {
        Log.newLog()
                .setLoggerName("")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
    }

    @Test
    void setLoggerName() {
        Log.newLog()
                .setLoggerName("test")
                .log();
        assertLog("test",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
    }

    @Test
    void setLevel() {
        Log.newLog()
                .setLevel(org.slf4j.event.Level.ERROR)
                .log();
        assertLog("default",
                Level.ERROR,
                null,
                "{\"level\":\"ERROR\",\"method\":\"anonymous\"}");
    }

    @Test
    void setTag() {
        Log.newLog()
                .setTag("tag")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"tag\":\"tag\",\"method\":\"anonymous\"}");
    }

    @Test
    void setClazz() {
        Log.newLog()
                .setClazz(LogTest.class)
                .log();
        assertLog("com.alibaba.framework3.logging.LogTest",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LogTest\"}");
    }

    @Test
    void setMethodName() {
        Log.newLog()
                .setMethodName("method")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"method\"}");
    }

    @Test
    void setMethodNameEmpty() {
        Log.newLog()
                .setMethodName("")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
    }

    @Test
    void setMethodNameWithClass() {
        Log.newLog()
                .setClazz(LogTest.class)
                .setMethodName("method")
                .log();
        assertLog("com.alibaba.framework3.logging.LogTest",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"LogTest.method\"}");
    }

    @Test
    void setMethodNameFullWithClass() {
        Log.newLog()
                .setClazz(LogTest.class)
                .setMethodName("Class.method")
                .log();
        assertLog("com.alibaba.framework3.logging.LogTest",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"Class.method\"}");
    }

    @Test
    void setSuccess() {
        Log.newLog()
                .setSuccess(true)
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"success\":true}");
    }

    @Test
    void setCode() {
        Log.newLog()
                .setCode("CODE")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"code\":\"CODE\"}");
    }

    @Test
    void setMessage() {
        Log.newLog()
                .setMessage("Message")
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"message\":\"Message\"}");
    }

    @Test
    void setArgs() {
        Log.newLog()
                .setArgs(new Object[]{1, 2})
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"args\":{\"p0\":1,\"p1\":2}}");
    }

    @Test
    void setArgsMismatch() throws Exception {
        Log.newLog()
                .setMethod(ArrayList.class.getMethod("add", Object.class))
                .setArgs(new Object[]{1, 2})
                .log();
        assertLog("java.util.ArrayList",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"ArrayList.add\",\"args\":{\"arg0\":1,\"p1\":2}}");
    }

    @Test
    void addArg() {
        Log.newLog()
                .addArg("key1", "value1")
                .addArg("key2", null)
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"args\":{\"key1\":\"value1\",\"key2\":null}}");
    }

    @Test
    void setResultNull() {
        Log.newLog()
                .setResult(null)
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"result\":null}");
    }

    @Test
    void setApiResultSuccessNullData() {
        Log.newLog()
                .setResult(Result.success())
                .log();
        assertLog("default",
                Level.INFO,
                null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"success\":true,\"result\":null}");
    }
}