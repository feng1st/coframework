package io.codeone.framework.chain.log;

import ch.qos.logback.classic.Level;
import io.codeone.framework.chain.composite.Sequential;
import io.codeone.framework.chain.context.Context;
import io.codeone.framework.common.log.util.LogUtils;
import io.codeone.framework.ext.BizScenario;
import io.codeone.framework.logging.shared.BaseLoggingTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChainLoggerTest extends BaseLoggingTest {

    @Autowired
    private ChainLoggerTestNodeProduce chainLoggerTestNodeProduce;
    @Autowired
    private ChainLoggerTestNodeConsume chainLoggerTestNodeConsume;
    @Autowired
    private ChainLoggerTestNodeAbility chainLoggerTestNodeAbility;

    @Test
    public void logDefault() {
        Sequential.of(chainLoggerTestNodeProduce, chainLoggerTestNodeConsume).run(Context.of());

        assertLogs("{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeProduce\",\"elapsed\":0}",
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeConsume\",\"elapsed\":0}");
    }

    @Test
    public void logChainName() {
        Sequential.of(chainLoggerTestNodeProduce, chainLoggerTestNodeConsume).run(Context.of().chainName("chain"));

        assertLogs("{\"chain\":\"chain\",\"node\":\"ChainLoggerTestNodeProduce\",\"elapsed\":0}",
                "{\"chain\":\"chain\",\"node\":\"ChainLoggerTestNodeConsume\",\"elapsed\":0}");
    }

    @Test
    public void logLambda() {
        Sequential.of(context -> {
                    context.put(String.class, "Hello chain");
                    return true;
                },
                context -> {
                    System.out.println(context.get(String.class));
                    return true;
                }).run(Context.of());

        assertLogs("{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}",
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}");
    }

    @Test
    public void logBreak() {
        Sequential.of(context -> {
                    context.put(String.class, "Hello chain");
                    return false;
                },
                context -> {
                    System.out.println(context.get(String.class));
                    return true;
                }).run(Context.of());

        assertLog("chain", Level.INFO, null,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"break\":true}");
    }

    @Test
    public void logException() {
        Assertions.assertThrows(IllegalStateException.class,
                () -> Sequential.of(context -> {
                            context.put(String.class, "Hello chain");
                            throw new IllegalStateException();
                        },
                        context -> {
                            System.out.println(context.get(String.class));
                            return true;
                        }).run(Context.of()));

        assertLog("chain", Level.ERROR, IllegalStateException.class,
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"exception\":\"java.lang.IllegalStateException\"}");
    }

    @Test
    public void logParam() {
        Sequential.of(context -> {
                    context.put(String.class, "Hello chain");
                    context.log("text", "Hello chain");
                    return true;
                },
                context -> {
                    System.out.println(context.get(String.class));
                    return true;
                }).run(Context.of());

        assertLogs("{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"text\":\"Hello chain\"}}",
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0}");
    }

    @Test
    public void logOnExecute() {
        Sequential.of(context -> {
                    context.put(String.class, "Hello chain");
                    return true;
                },
                context -> {
                    System.out.println(context.get(String.class));
                    return true;
                }).run(Context.of(Long.class, 10000L).onExecute(context -> {
            context.ifPresent(Long.class, o -> context.log("traceId", o));
            context.ifPresent(String.class, o -> context.log("text", o));
        }));

        assertLogs("{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"traceId\":10000}}",
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTest$$Lambda\",\"elapsed\":0,\"params\":{\"traceId\":10000,\"text\":\"Hello chain\"}}");
    }

    @Test
    public void route() {
        Assertions.assertEquals("foo", chainLoggerTestNodeAbility.run(Context.of().bizScenario(BizScenario.ofBizId("foo")), String.class));
        Assertions.assertEquals("bar", chainLoggerTestNodeAbility.run(Context.of().bizScenario(BizScenario.ofBizId("bar")), String.class));

        assertLogs("{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeAbilityForFoo\",\"bizId\":\"foo\",\"scenario\":\"*\",\"elapsed\":0}",
                "{\"chain\":\"anonymous\",\"node\":\"ChainLoggerTestNodeAbilityForBar\",\"bizId\":\"bar\",\"scenario\":\"*\",\"elapsed\":0}");
    }

    @Test
    public void logNonJson() {
        LogUtils.logAsJson = false;
        Assertions.assertThrows(IllegalStateException.class,
                () -> Sequential.of(context -> {
                            context.put(String.class, "Hello chain");
                            throw new IllegalStateException();
                        },
                        context -> {
                            System.out.println(context.get(String.class));
                            return true;
                        }).run(Context.of()));
        LogUtils.logAsJson = true;

        assertLog("chain", Level.ERROR, IllegalStateException.class,
                "{chain=anonymous, node=ChainLoggerTest$$Lambda, elapsed=0, exception=java.lang.IllegalStateException}");
    }
}