# Co-Framework

优雅高效的Java业务框架。

## 1. API

框架为API层提供了参数校验、异常转失败结果、调用日志等增强功能。

### 1.1 快速开始

#### 1.1.1 二方包

假设你的业务应用由两部分组成，`biz-client`用于对外暴露接口，`biz-service`用于实现业务逻辑。则：

在`biz-client`中引入`coframework-api`，用于引入接口所需模型：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

在`biz-service`中引入`coframework-api-core`，用于自动生效增强功能：

```xml

<dependency>
    <groupId>io.codeone</groupId>
    <artifactId>coframework-api-core</artifactId>
    <version>1.0.0</version>
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

## 2. Plugin System

### 2.1 Quick Start

## 3. Chain System

### 3.1 Quick Start

## 4. Extension System

### 4.1 Quick Start

## 5. Logging

### 5.1 Quick Start
