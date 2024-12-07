# Co-Framework

优雅高效的Java业务框架。

## 1. API

框架为API层提供了参数校验、异常转失败结果、调用日志等增强功能。

### 1.1 快速开始

#### 1.1.1 二方包

假设你的业务应用由两部分组成，`biz-client`用于对外暴露接口，`biz-service`用于实现业务逻辑。则：

- 在`biz-client`中引入`coframework-api`，用于引入接口所需模型：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-api</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

- 在`biz-service`中引入`coframework-api-core`，用于自动生效增强功能：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-api-core</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

#### 1.1.2 参数校验

1. 在接口服务或者方法上添加`@API`注解：

```java

@API // 服务上，对所有方法生效
public class BizApiImpl implements BizApi {

    @API // 或者方法上，对当前方法生效
    public Result<BizData> getData(BizParam param) {
        // ...
    }
}
```

2. 接口参数继承自`BaseParam`或者`BasePageParam`，并实现`validate()`方法：

```java
public class BizParam extends BaseParam {
    @Override
    public void validate() {
        Validator.requireNonNull(userId, "userId is null");
    }
}
```

#### 1.1.3 异常转失败结果

1. 在接口服务或者方法上添加`@API`注解。
2. 使用`Result`包装接口的返回：

```java
// 方法会返回一个失败的Result，而不是抛出异常
public Result<BizData> getData(BizParam param) {
    // ...
    throw new BizException(CODE, MESSAGE);
}
```

#### 1.1.4 调用日志

在接口服务或者方法上添加`@API`注解，则接口调用会自动记录日志。

### 1.2 高级用法

#### 1.2.1 定制异常转失败结果

1. 如果希望定制结果的`errorCode`（默认为异常类名），可以让异常实现`ApiException`接口，比如继承自`BaseException`。
   此时`Result.errorCode`会使用`ApiException.code`。
2. 如果希望定制结果的`errorMessage`（默认为异常消息），可以使用`@CustomErrorMessage`注解：

```java

@CustomErrorMessage("System is busy, please try again later.")
public Result<BizData> getData(BizParam param) {
    // ...
}
```

#### 1.2.2 定制调用日志

可以通过使用`@Logging`注解，进一步控制调用日志的行为，比如是否记录参数、是否记录返回值。

```java

@Logging(logArgs = false, logResult = false)
public Result<BizData> getData(BizParam param) {
    // ...
}
```

具体请参考<5. 日志>。

### 1.3 支持现有系统

框架支持不修改接口签名，直接在现有系统上应用增强能力。

#### 1.3.1 参数校验

请参考`ArgCheckingApiPlugin`，编写插件为现有参数类型增加校验能力。

#### 1.3.2 异常转失败结果

请参考`ExToResultApiPlugin`，编写插件转化异常为现有结果包装类型。

#### 1.3.3 调用日志

为了使用旧模型时，依然能正确的记录调用是否成功、错误码和错误消息等信息，可以注册用于旧模型的`ApiResultConverter`和
`ApiExceptionConverter`的Spring bean。

```java

@Component
public class OldResultConverter<T> implements ApiResultConverter<OldResult<T>> {

    @Override
    public ApiResult<T> convert(OldResult<T> source) {
        return source.isSuccess()
                ? Result.success(source.getData())
                : Result.failure(source.getErrorCode(), source.getErrorMessage());
    }
}

@Component
public class OldExceptionConverter implements ApiExceptionConverter<OldException> {

    @Override
    public ApiException convert(OldException source) {
        return source::getCode;
    }
}
```

---

## 2. 插件系统

插件系统提供了类似AOP语法，但是更加灵活友好的切面拦截能力。

### 2.1 快速开始

#### 2.1.1 二方包

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-plugin</artifactId>
    <version>${coframework.version}</version>
</dependency>
```

#### 2.1.2 定义和使用插件

通过实现`Plugin`接口生成插件，通过`@Plug`注解指定生效阶段和目标注解（此处为`@BizProcess`）：

```java

@Plug(value = Stages.BEFORE_TARGET, targetAnnotations = BizProcess.class)
public class BizProcessPlugin implements Plugin {

    @Override
    public void before(Method method, Object[] args) {
        // ...
    }
}
```

通过应用目标注解启用插件：

```java

@BizProcess
public Result<BizData> getData(BizParam param) {
    // ...
}
```

### 2.2 高级用法

#### 2.2.1 动态启用

可以通过`@EnablePlugin`注解动态启用插件：

```java

@EnablePlugin({FooPlugin.class, BarPlugin.class})
public Result<BizData> getData(BizParam param) {
    // ...
}
```

这些插件不必指定`@Plug.targetAnnotations`。

#### 2.2.2 动态绑定

可以通过注册`AnnoPluginBinding`bean来实现动态绑定，而不用提前指定`@Plug.targetAnnotations`。
此方法常用来绑定已存在的插件。

```java

@Bean
public AnnoPluginBinding bizProcessBinding() {
    return AnnoPluginBinding.of(BizProcess.class, BizProcessPlugin.class);
}
```

#### 2.2.3 通过SPI绑定

和动态绑定类似，但是通过SPI在项目启动早期建立绑定关系。
此方法主要解决复杂业务应用可能出现的插件循环依赖导致加载失败的问题。

1. 实现`AnnoPluginBindingFactory`工厂类，提供动态绑定关系：

```java
public class BizPluginBindingFactory implements AnnoPluginBindingFactory {

    @Override
    public List<AnnoPluginBinding> getBindings() {
        return Arrays.asList(
                AnnoPluginBinding.of(BizProcess.class, BizProcessPlugin.class));
    }
}
```

2. 在项目的`META-INF/spring.factories`中引入实现的工厂类：

```properties
io.codeone.framework.plugin.binding.AnnoPluginBindingFactory=\
  com.biz.config.BizPluginBindingFactory
```

#### 2.2.4 插件顺序

框架定义了8个标准阶段用于决定插件执行顺序：

| 阶段                       | 顺序值 | 主逻辑      |
|--------------------------|-----|----------|
| PRE_ARG_INTERCEPTING     | 0   | before() |
| ARG_INTERCEPTING         | 1   | before() |
| POST_ARG_INTERCEPTING    | 2   | before() |
| BEFORE_TARGET            | 3   | before() |
| AFTER_TARGET             | -4  | after()  |
| PRE_RESULT_INTERCEPTING  | -5  | after()  |
| RESULT_INTERCEPTING      | -6  | after()  |
| POST_RESULT_INTERCEPTING | -7  | after()  |

多个插件的执行顺序类似于堆栈。如图所示，`POST_RESULT_INTERCEPTING`阶段插件的`before()`方法，甚至早于`PRE_ARG_INTERCEPTING`
阶段插件的`before()`方法。请额外注意插件**各方法**的执行顺序，将主逻辑放在正确的方法里。

![](docs/images/order-of-plugins.png)

如果需要精细控制同一阶段中不同插件的先后顺序，可以搭配`org.springframework.core.annotation.Order`使用：

```java

@Plug(value = Stages.BEFORE_TARGET)
@Order(1)
public class BizProcessPlugin implements Plugin {
    // ...
}
```

---

## 3. Chain System

### 3.1 Quick Start

---

## 4. Extension System

### 4.1 Quick Start

---

## 5. Logging

### 5.1 Quick Start