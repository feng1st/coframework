# Co-Framework

优雅高效的 Java 业务框架

---

**Co-Framework** 是一款专为现代 Java 开发者设计的业务框架，提供开箱即用的 API 增强、灵活的插件系统、链式调用以及可扩展的业务能力路由。
通过简化重复劳动、增强代码一致性和提升扩展能力，它显著加速开发流程并降低维护成本。适用于需要高效开发、灵活扩展的复杂业务场景。

---

## 快速开始

以下是如何快速体验 Co-Framework 的核心功能：

### 1. API 增强：一行注解实现多项增强

#### 功能亮点

- 参数校验：自动调用 `validate()` 方法，确保输入合法性。
- 异常包装：异常自动转化为友好的接口返回结果。
- 调用日志：统一记录成功、失败或异常的接口调用日志。

#### 快速上手

1. 添加 Maven 依赖：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-api-core</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

2. 在接口服务上添加 `@API` 注解：

```java

@API // 类级注解
public class BizApiImpl implements BizApi {

    @API // 方法级注解
    public Result<BizData> getData(BizParam param) {
        // ...
    }
}
```

3. 参数校验示例：

```java
public class BizParam extends BaseParam {
    @Override
    public void validate() {
        Validator.requireNonNull(userId, "userId is null");
    }
}
```

4. 异常自动转化为失败结果：

```java
public Result<BizData> getData(BizParam param) {
    throw new BizException(CODE, MESSAGE);
}
```

5. 查看调用日志：

接口调用日志会记录调用结果、错误码、错误信息等，无需额外配置。

### 2. 插件系统：灵活的拦截能力

#### 功能亮点

- 类似 AOP，但更灵活，适合复杂场景。
- 支持多阶段、多插件动态绑定。

#### 快速上手

1. 添加 Maven 依赖：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-plugin</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

2. 定义插件：

```java

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = BizProcess.class)
public class BizProcessPlugin implements Plugin {
    @Override
    public void before(Method method, Object[] args) {
        // 插件逻辑
    }
}
```

3. 应用插件：

```java

@BizProcess
public Result<BizData> getData(BizParam param) {
    // ...
}
```

### 3. 链系统：灵活的流程编排

#### 功能亮点

- 支持链式调用，简化复杂业务流程。
- 节点独立、可复用，增强代码模块化。

#### 快速上手

1. 添加 Maven 依赖：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-chain</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

2. 定义链节点：

```java

@Component
public class Produce implements Chainable {
    @Override
    public boolean execute(Context context) {
        context.put(String.class, "content");
        return true;
    }
}

@Component
public class Consume implements Chainable {
    @Override
    public boolean execute(Context context) {
        assert "content".equals(context.get(String.class));
        return true;
    }
}
```

3. 编排并执行链：

```java

@Service
public class ChainService {
    @Autowired
    private Produce produce;
    @Autowired
    private Consume consume;

    public void run() {
        Sequential.of(produce, consume).run(Context.of());
    }
}
```

### 4. 扩展系统：动态路由到多实现

#### 功能亮点

- 针对不同业务场景动态路由到不同实现。
- 提升代码扩展性，减少重复开发。

#### 快速上手

1. 添加 Maven 依赖：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-ext</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

2. 定义可扩展接口：

```java

@Ability
public interface BizAbility {
    void execute(BizScenario bizScenario);
}
```

3. 提供不同业务场景的扩展实现：

```java

@Extension(bizId = "manager")
public class BizAbilityForManager implements BizAbility {
    @Override
    public void execute(BizScenario bizScenario) {
        // ...
    }
}
```

4. 动态路由调用：

```java

@Service
public class BizService {
    @Autowired
    private BizAbility bizAbility;

    public void executeBizAbility() {
        bizAbility.execute(BizScenario.ofBizId("manager"));
    }
}
```

---

## 高级用法与定制

- **定制异常处理和日志：** 详见 [API 增强文档](#api增强)。
- **插件顺序控制与动态绑定：** 详见 [插件系统文档](#插件系统)。
- **链系统并行执行与上下文管理：** 详见 [链系统文档](#链系统)。
- **扩展系统业务场景路由：** 详见 [扩展系统文档](#扩展系统)。

---

**查看更多文档与示例项目**：

- GitHub 示例项目：[Co-Framework Example](https://github.com/codeone/coframework-example)
- 完整 Javadoc：[API 文档](https://codeone.io/docs)

让 Co-Framework 助力您的业务开发之旅！
