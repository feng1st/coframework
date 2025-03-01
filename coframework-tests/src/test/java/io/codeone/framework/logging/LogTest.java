package io.codeone.framework.logging;

import ch.qos.logback.classic.Level;
import io.codeone.framework.api.response.Result;
import io.codeone.framework.common.log.util.LogFormat;
import io.codeone.framework.shared.BaseLoggingTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
class LogTest extends BaseLoggingTest {

    @Test
    void setLogger() {
        test(() -> {
            Log.newLog()
                    .setLogger(log)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
        assertLog(1, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=INFO method=anonymous");
        assertLog(2, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=>INFO||method=>anonymous");
    }

    @Test
    void setLoggerNameEmpty() {
        test(() -> {
            Log.newLog()
                    .setLoggerName("")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous");
    }

    @Test
    void setLoggerName() {
        test(() -> {
            Log.newLog()
                    .setLoggerName("test")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "test", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
        assertLog(1, 3, "test", Level.INFO, null,
                "level=INFO method=anonymous");
        assertLog(2, 3, "test", Level.INFO, null,
                "level=>INFO||method=>anonymous");
    }

    @Test
    void setLevel() {
        test(() -> {
            Log.newLog()
                    .setLevel(org.slf4j.event.Level.ERROR)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.ERROR, null,
                "{\"level\":\"ERROR\",\"method\":\"anonymous\"}");
        assertLog(1, 3, "default", Level.ERROR, null,
                "level=ERROR method=anonymous");
        assertLog(2, 3, "default", Level.ERROR, null,
                "level=>ERROR||method=>anonymous");
    }

    @Test
    void setClazz() {
        test(() -> {
            Log.newLog()
                    .setClazz(LogTest.class)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LogTest\"}");
        assertLog(1, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=INFO method=LogTest");
        assertLog(2, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=>INFO||method=>LogTest");
    }

    @Test
    void setMethodName() {
        test(() -> {
            Log.newLog()
                    .setMethodName("method")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"method\"}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=method");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>method");
    }

    @Test
    void setMethodNameEmpty() {
        test(() -> {
            Log.newLog()
                    .setMethodName("")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\"}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous");
    }

    @Test
    void setMethodNameWithClass() {
        test(() -> {
            Log.newLog()
                    .setClazz(LogTest.class)
                    .setMethodName("method")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"LogTest.method\"}");
        assertLog(1, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=INFO method=LogTest.method");
        assertLog(2, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=>INFO||method=>LogTest.method");
    }

    @Test
    void setMethodNameFullWithClass() {
        test(() -> {
            Log.newLog()
                    .setClazz(LogTest.class)
                    .setMethodName("Class.method")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"Class.method\"}");
        assertLog(1, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=INFO method=Class.method");
        assertLog(2, 3, "io.codeone.framework.logging.LogTest", Level.INFO, null,
                "level=>INFO||method=>Class.method");
    }

    @Test
    void setSuccess() {
        test(() -> {
            Log.newLog()
                    .setSuccess(true)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"success\":true}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous success=true");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||success=>true");
    }

    @Test
    void setCode() {
        test(() -> {
            Log.newLog()
                    .setCode("CODE")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"code\":\"CODE\"}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous code=CODE");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||code=>CODE");
    }

    @Test
    void setMessage() {
        test(() -> {
            Log.newLog()
                    .setMessage("Message")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"message\":\"Message\"}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous message=Message");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||message=>Message");
    }

    @Test
    void putContext() {
        test(() -> {
            Log.newLog()
                    .putContext("tag", "tag")
                    .putContext("userId", 123)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"ctx\":{\"tag\":\"tag\",\"userId\":123}}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous ctx.tag=tag ctx.userId=123");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||ctx.tag=>tag||ctx.userId=>123");
    }

    @Test
    void setArgs() {
        test(() -> {
            Log.newLog()
                    .setArgs(new Object[]{1, 2})
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"args\":{\"p0\":1,\"p1\":2}}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous args.p0=1 args.p1=2");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||args.p0=>1||args.p1=>2");
    }

    @Test
    void setArgsMismatch() throws Exception {
        test(() -> {
            Log.newLog()
                    .setMethod(ArrayList.class.getMethod("add", Object.class))
                    .setArgs(new Object[]{1, 2})
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "java.util.ArrayList", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"ArrayList.add\",\"args\":{\"arg0\":1,\"p1\":2}}");
        assertLog(1, 3, "java.util.ArrayList", Level.INFO, null,
                "level=INFO method=ArrayList.add args.arg0=1 args.p1=2");
        assertLog(2, 3, "java.util.ArrayList", Level.INFO, null,
                "level=>INFO||method=>ArrayList.add||args.arg0=>1||args.p1=>2");
    }

    @Test
    void addArg() {
        test(() -> {
            Log.newLog()
                    .addArg("key1", "value1")
                    .addArg("key2", null)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"args\":{\"key1\":\"value1\",\"key2\":null}}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous args.key1=value1 args.key2=null");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||args.key1=>value1||args.key2=>null");
    }

    @Test
    void setResultNull() {
        test(() -> {
            Log.newLog()
                    .setResult(null)
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"result\":null}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous result=null");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||result=>null");
    }

    @Test
    void setApiResultSuccessNullData() {
        test(() -> {
            Log.newLog()
                    .setResult(Result.success())
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"success\":true,\"result\":null}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous success=true result=null");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||success=>true||result=>null");
    }

    @Test
    void specialKv() {
        test(() -> {
            Log.newLog()
                    .putContext(null, null)
                    .putContext("", "")
                    .putContext("中文", "中文")
                    .log();
        }, LogFormat.JSON, LogFormat.LOG_FMT, LogFormat.CUSTOM);
        assertLog(0, 3, "default", Level.INFO, null,
                "{\"level\":\"INFO\",\"method\":\"anonymous\",\"ctx\":{\"null\":null,\"\":\"\",\"__\":\"中文\"}}");
        assertLog(1, 3, "default", Level.INFO, null,
                "level=INFO method=anonymous ctx.null=null ctx.=\"\" ctx.__=中文");
        assertLog(2, 3, "default", Level.INFO, null,
                "level=>INFO||method=>anonymous||ctx.null=>null||ctx.=>\"\"||ctx.__=>中文");
    }
}