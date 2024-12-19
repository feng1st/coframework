# Co-Framework

An Elegant and Efficient Java Business Framework

---

**Co-Framework** is a business framework designed for modern Java developers. It offers out-of-the-box API enhancements,
a flexible plugin system, chained invocation, and extensible routing for business capabilities. By simplifying
repetitive tasks, enhancing code consistency, and improving extensibility, Co-Framework significantly accelerates
development and lowers maintenance costs. It’s ideal for complex business scenarios that require efficient development
and flexible extension.

---

## 1. Getting Started

Follow these steps to quickly experience the core features of Co-Framework:

### 1.1 API Enhancements: Achieve Multiple Improvements with a Single Annotation

#### 1.1.1 Key Features

- **Parameter Validation:** Automatically invokes `validate()` to ensure input validity.
- **Exception Wrapping:** Exceptions are automatically converted into user-friendly responses.
- **Invocation Logging:** Unified logs for successful, failed, or exceptional API calls.

#### 1.1.2 Quick Start

1. Add the Maven dependency:

   ```xml
   <dependency>
       <groupId>io.codeone</groupId>
       <artifactId>coframework-api-core</artifactId>
       <version>${coframework.version}</version>
   </dependency>
   ```

2. Add the `@API` annotation to your service interface:

   ```java
   @API // Class-level annotation applies to all methods
   public class BizApiImpl implements BizApi {

       @API // Method-level annotation applies to this specific method
       public Result<BizData> getData(BizParam param) {
           // ...
       }
   }
   ```

3. Parameter validation example:

   ```java
   public class BizParam extends BaseParam {
       @Override
       public void validate() {
           Validator.requireNonNull(userId, "userId is null");
       }
   }
   ```

4. Exceptions are automatically converted into error responses:

   ```java
   // Instead of throwing an exception, the framework returns a failure result
   public Result<BizData> getData(BizParam param) {
       throw new BizException(CODE, MESSAGE);
   }
   ```

5. View invocation logs:

   API invocation logs record results, error codes, and error messages with no extra configuration required.

### 1.2 Plugin System: Flexible Interception Capabilities

#### 1.2.1 Key Features

- Similar to AOP but more flexible, suitable for complex scenarios.
- Supports multi-stage, multi-plugin dynamic binding.

#### 1.2.2 Quick Start

1. Add the Maven dependency:

   ```xml
   <dependency>
       <groupId>io.codeone</groupId>
       <artifactId>coframework-plugin</artifactId>
       <version>${coframework.version}</version>
   </dependency>
   ```

2. Define a plugin:

   ```java
   // Specify plugin order and target annotation
   @Plug(value = Stages.BEFORE_TARGET, targetAnnotations = BizProcess.class)
   public class BizProcessPlugin implements Plugin {
       @Override
       public void before(Method method, Object[] args) {
           // Plugin logic
       }
   }
   ```

3. Apply the plugin:

   ```java
   @BizProcess // The target annotation that activates the plugin
   public Result<BizData> getData(BizParam param) {
       // ...
   }
   ```

### 1.3 Chain System: Flexible Process Orchestration

#### 1.3.1 Key Features

- Supports chained invocation to simplify complex business flows.
- Independent, reusable nodes improve code modularity.

#### 1.3.2 Quick Start

1. Add the Maven dependency:

   ```xml
   <dependency>
       <groupId>io.codeone</groupId>
       <artifactId>coframework-chain</artifactId>
       <version>${coframework.version}</version>
   </dependency>
   ```

2. Define chain nodes:

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

3. Arrange and run the chain:

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

### 1.4 Extension System: Dynamic Routing to Multiple Implementations

#### 1.4.1 Key Features

- Dynamically route to different implementations based on varying business scenarios.
- Enhance code extensibility and reduce duplicate development.

#### 1.4.2 Quick Start

1. Add the Maven dependency:

   ```xml
   <dependency>
       <groupId>io.codeone</groupId>
       <artifactId>coframework-ext</artifactId>
       <version>${coframework.version}</version>
   </dependency>
   ```

2. Define an extensible interface:

   ```java
   @Ability
   public interface BizAbility {
       void execute(BizScenario bizScenario);
   }
   ```

3. Provide different implementations for various business scenarios:

   ```java
   @Extension(bizId = "manager")
   public class BizAbilityForManager implements BizAbility {
       @Override
       public void execute(BizScenario bizScenario) {
           // ...
       }
   }
   ```

4. Dynamic routing at runtime:

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

## 2. Advanced Usage of API Enhancements

The framework provides more advanced features to help developers customize exception handling, invocation logging, and
integration with existing systems. The following outlines these capabilities.

### 2.1 Customizing Exception Wrapping

1. If you want to customize the `errorCode` (by default, it’s the exception class name), let the exception implement the
   `ApiException` interface, for example by extending `BaseException`. In this case, `errorCode` will be retrieved from
   `getCode()`.
2. To customize the `errorMessage` (which by default is the exception message), for example to hide technical details
   from end users, use the `@CustomErrorMessage` annotation:

   ```java
   @API
   @CustomErrorMessage("System is busy, please try again later.")
   public Result<BizData> getData(BizParam param) {
       // ...
   }
   ```

### 2.2 Customizing Invocation Logging

You can use the `@Logging` annotation to further control invocation logging behavior—such as whether to log parameters
or return values:

```java

@API
@Logging(logArgs = false, logResult = false)
public Result<BizData> getData(BizParam param) {
    // ...
}
```

For detailed logging configurations, see **Section 6: Logging**.

### 2.3 Integrating with Existing Systems

The framework can enhance existing APIs by applying the same enhancements:

1. Refer to `ArgCheckingApiPlugin` as an example of writing a plugin to add validation to existing parameter types.
2. Refer to `ExToResultApiPlugin` for an example of converting exceptions into existing result wrapper formats.
3. To ensure that exception-to-failure conversion and logging can correctly identify success states, error codes, and
   messages in older models, register Spring beans for `ApiResultConverter` and `ApiExceptionConverter` for legacy
   models:

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

## 3. Advanced Usage of the Plugin System

The plugin system in Co-Framework provides powerful extension capabilities suitable for complex business scenarios. In
this section, we’ll introduce how to control plugin execution order, dynamically bind plugins, and manage plugins more
intricately through SPI.

### 3.1 Plugin Execution Order

- The framework defines 8 standard stages to control plugin execution order:

  | Stage                     | Order Value | Logic       |
  |---------------------------|-------------|-------------|
  | PRE_ARG_INTERCEPTING      | 0           | before()    |
  | ARG_INTERCEPTING          | 1           | before()    |
  | POST_ARG_INTERCEPTING     | 2           | before()    |
  | BEFORE_TARGET             | 3           | before()    |
  | AFTER_TARGET              | -4          | after()     |
  | PRE_RESULT_INTERCEPTING   | -5          | after()     |
  | RESULT_INTERCEPTING       | -6          | after()     |
  | POST_RESULT_INTERCEPTING  | -7          | after()     |

Multiple plugins execute in a stack-like manner.

For instance, the `before()` method of a plugin in the `AFTER_TARGET` stage may run even before a `before()` method in
the `BEFORE_TARGET` stage.

Therefore, pay close attention to the execution order of **each method** within plugins, making sure that the main logic
is placed in the correct method.

![](docs/images/order-of-plugins.png)

- If you need finer control over the order of plugins within the same stage, use `@Order` from
  `org.springframework.core.annotation.Order`:

  ```java
  @Plug(value = Stages.BEFORE_TARGET)
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public class BizProcessPlugin implements Plugin {
      // ...
  }
  ```

### 3.2 Dynamic Binding and Activation

#### 3.2.1 Dynamic Activation

Use the `@EnablePlugin` annotation to dynamically activate plugins:

```java

@EnablePlugin({FooPlugin.class, BarPlugin.class})
public Result<BizData> getData(BizParam param) {
    // ...
}
```

These plugins don’t need `@Plug.targetAnnotations`.

#### 3.2.2 Dynamic Binding

You can register an `AnnoPluginBinding` bean to dynamically bind plugins without predefining `@Plug.targetAnnotations`.

This is commonly used to bind existing plugins:

```java

@Bean
public AnnoPluginBinding bizProcessBinding() {
    return AnnoPluginBinding.of(BizProcess.class, BizProcessPlugin.class);
}
```

#### 3.2.3 Binding via SPI

Similar to dynamic binding, but established early during project startup via SPI. This approach solves issues with
plugin circular dependencies in complex applications.

1. Implement an `AnnoPluginBindingFactory` that provides binding relationships:

   ```java
   public class BizPluginBindingFactory implements AnnoPluginBindingFactory {
       @Override
       public List<AnnoPluginBinding> getBindings() {
           return Arrays.asList(
                   AnnoPluginBinding.of(BizProcess.class, BizProcessPlugin.class));
       }
   }
   ```

2. Include the factory in your project’s `META-INF/spring.factories`:

   ```properties
   io.codeone.framework.plugin.binding.AnnoPluginBindingFactory=\
     com.biz.config.BizPluginBindingFactory
   ```

---

## 4. Advanced Usage of the Chain System

The chain system of Co-Framework provides flexible process orchestration capabilities, enabling complex business logic
to be organized using chained invocations for simplicity and modularity. This section introduces node types, process
orchestration, context management, and other advanced features to help you build more efficient business flows.

### 4.1 Chain Nodes

#### 4.1.1 Node Types

The chain system offers the following node types:

| Node Type   | Description                                                                                                    |
|-------------|----------------------------------------------------------------------------------------------------------------|
| Chainable   | Provides an `execute` method. Returns `false` to end the chain, otherwise continues to the next node.          |
| Continuous  | Provides an `executeAndContinue` method with no return value. The chain does not stop at this node.            |
| Conditional | Nodes that execute conditionally.                                                                              |
| Empty       | An empty node that does nothing.                                                                               |
| Sequential  | Executes member nodes in sequence.                                                                             |
| Parallel    | Executes member nodes in parallel if a thread pool is available; otherwise falls back to sequential execution. |

#### 4.1.2 Lambda Expressions

`Chainable` nodes can be defined using lambda expressions. For example:

```java

@Service
public class ChainService {
    public void run() {
        Sequential.of(
                context -> {
                    context.put(String.class, "content");
                    return true;
                },
                context -> {
                    assert "content".equals(context.get(String.class));
                    return true;
                }
        ).run(Context.of());
    }
}
```

#### 4.1.3 Process Orchestration

Use `Sequential` and `Parallel` to orchestrate processes:

```java
private Chainable getChain() {
    // The overall production-consumption process runs sequentially
    return Sequential.of(
            // Parallel production
            Parallel.of(
                    mapProduceFoo,
                    mapProduceBar,
                    mapProduceBaz
            ),
            // Production aggregation
            reduceProduce,
            // Parallel consumption
            Parallel.of(
                    mapConsumeFoo,
                    mapConsumeBar,
                    mapConsumeBaz
            ),
            // Consumption aggregation
            reduceConsume
    );
}
```

### 4.2 Context

`Context` holds a map of parameters, providing common map operations such as `get`, `put`, and `computeIfAbsent`. It is
thread-safe.

#### 4.2.1 Overall Input and Output

You can pass input and retrieve output for the entire chain through the context:

```java
public void run(Input input) {
    getChain().run(Context.of(Input.class, input));
}

public Output runAndReturn(Input input) {
    // Automatically returns context.get(Output.class)
    return getChain().run(Context.of(Input.class, input), Output.class);
}
```

#### 4.2.2 Parameter Types

Context parameters can be of any key type, including strings and enums. If you use `Class<?>` or `Typed` as a parameter
key, the framework performs type checks when reading and writing parameters.

Here’s how to define `Typed` parameters:

```java

@RequiredArgConstructor
@Getter
public enum TypedParamEnum implements Typed {
    INPUT(Input.class),
    OUTPUT(Output.class),
    ;
    private final Class<?> type;
}
```

#### 4.2.3 Thread Pools

If the context provides a thread pool, the nodes within `Parallel` will be executed concurrently. Ensure that these
nodes are thread-safe.

```java
public void run(Input input) {
    getChain().run(Context.of()
            .threadPool(ForkJoinPool.commonPool()));
}
```

### 4.3 Logging

The execution of chain nodes is logged, producing the following format:

```json5
{
  // Specified by context.of().chainName("chainName"), defaults to "anonymous"
  "chain": "chainName",
  // If using BizContext.of(BizScenario.of("bizId", "scenario")), bizId and scenario are recorded
  "bizId": "bizId",
  "scenario": "scenario",
  "node": "ClassNameOfNode",
  "elapsed": 0,
  // If the chain node throws an exception, it will be recorded
  "exception": "stringOfException",
  // If the chain node returns false, "break" will be recorded
  "break": true,
  // Use context.log("key", value) to record key-value pairs
  "params": {
    "key": {}
  }
}
```

- Setting the chain name:

  ```java
  public void run() {
      getChain().run(Context.of().chainName("chainName"));
  }
  ```

- Logging as needed:

  ```java
  @Component
  public class Produce implements Chainable {
      @Override
      public boolean execute(Context context) {
          // ...
          context.log("key", value);
          // ...
      }
  }
  ```

- Unified logging for every node:

  The context provides an `onExecute` method to configure logging behavior uniformly during chain execution, ensuring
  consistent and traceable logs.

  ```java
  public void run(Input input) {
      getChain().run(Context.of(Input.class, input)
              .onExecute(context -> {
                  // For each node, log userId to help trace the execution chain
                  context.<Input>ifPresent(Input.class, o -> context.log("userId", o.getUserId()));
              }));
  }
  ```

### 4.4 Extensible Chain Nodes

By using extensible interfaces (instead of concrete classes) as chain nodes, you can achieve node
extensibility—dynamically routing to different chain node implementations at runtime based on the business scenario in
the context.

(For the concepts of extensible (“Ability”) interfaces, extensions (“Extension”), and business scenarios (
`BizScenario`), see **Section 5: Extension System**.)

1. Define an extensible interface as a chain node:

   ```java
   // Note that this node is an interface, not a concrete class
   @Ability
   public interface Consume extends Chainable {
   }
   ```

2. Provide different implementations for different business scenarios:

   ```java
   // Implementation for the bizId "foo" scenario
   @Extension(bizId = "foo")
   public class ConsumeForFoo implements Consume {
       // ...
   }
   ```

3. Invoking the extensible chain node:

   ```java
   @Service
   public class ChainService {
       @Autowired
       private Produce produce;
       // Reference the interface rather than a specific class
       @Autowired
       private Consume consume;

       public void run() {
           Sequential.of(produce, consume)
                   // Use BizContext (a subclass of Context) to set business scenario
                   .run(BizContext.of(
                       // Because the bizId matches, the consume call routes to ConsumeForFoo
                       BizScenario.ofBizId("foo")));
       }
   }
   ```

---

## 5. Advanced Usage of the Extension System

Co-Framework’s extension system is designed to enhance flexibility and extensibility. By using extensible interfaces and
a dynamic routing mechanism, you can choose the appropriate implementation at runtime based on various business
scenarios. This section explains how to use extensible interfaces, extension implementations, and business scenarios to
handle more complex business needs.

### 5.1 Extensible Interfaces

An “Extensible” interface can have multiple extension implementations. At runtime, the framework routes to a specific
implementation based on parameters or the current business scenario.

The extension system provides two annotations, `@Ability` and `@ExtensionPoint`. Both function similarly, but differ in
semantics:

- **`@Ability`**: Generally represents a higher-level, abstract, and extensible capability or function.
- **`@ExtensionPoint`**: Generally represents a lower-level, more concrete extension point, often used for rules,
  configurations, etc.

Example:

```java

@Ability
public interface BizAbility {
    void execute(BizScenario bizScenario);
}
```

### 5.2 Extension Implementations

An “Extension” is an implementation of an extensible interface that applies to a particular business scenario.

Use the `@Extension` annotation to mark an implementation and specify the business scenario coordinates it supports. For
example:

```java

@Extension(bizId = "region.branch", scenarios = {"weekday.monday", "weekday.tuesday"})
public class BizAbilityForBranchMonday implements BizAbility {
    // ...
}
```

### 5.3 Business Scenarios

A `BizScenario` includes the caller’s business identity (`bizId`) and scenario (`scenario`), serving as the routing
coordinates for finding the correct extension implementation.

Both `bizId` and `scenario` support hierarchical structures. When routing, the system tries to match from the most
specific to the more general.

For example, if your `BizScenario` is `"region.branch|weekday.monday"`, the extension system will attempt to find a
match in this order (stop at the first match):

1. `@Extension(bizId = "region.branch", scenarios = "weekday.monday")`
2. `@Extension(bizId = "region.branch", scenarios = "weekday")`
3. `@Extension(bizId = "region.branch", scenarios = "*")`
4. `@Extension(bizId = "region", scenarios = "weekday.monday")`
5. `@Extension(bizId = "region", scenarios = "weekday")`
6. `@Extension(bizId = "region", scenarios = "*")`
7. `@Extension(bizId = "*", scenarios = "weekday.monday")`
8. `@Extension(bizId = "*", scenarios = "weekday")`
9. `@Extension(bizId = "*", scenarios = "*")`

### 5.4 Obtaining Routing Parameters

When calling an extensible interface, the extension system obtains routing parameters (the business scenario) from two
sources:

1. A method parameter of type `BizScenarioParam`, whose `getBizScenario()` method returns the business scenario.
2. The `BizScenarioContext`, which internally maintains a stack of business scenarios, with the top of the stack holding
   the scenario used in the latest routing.

#### 5.4.1 Retrieval Process

By default, the extension system tries the following steps to obtain routing parameters:

1. If there are no parameters in the method, get from the context.
2. If the method has exactly one parameter annotated with `@RouteBy`, and it is of type `BizScenarioParam`, use that
   parameter.
3. If the method or service is annotated with `@RouteByContext`, get from the context.
4. If the method has exactly one `BizScenarioParam`-type parameter (without `@RouteBy`), use that parameter.
5. Otherwise, fallback to the context.

If the context also provides nothing, an exception is thrown.

#### 5.4.2 Routing Context

You can manually push a business scenario into the context for subsequent execution steps:

```java
public void run() {
    // bizScenario is available throughout process()
    BizScenarioContext.invoke(bizScenario, this::process);
}
```

Note: The context is `ThreadLocal` and does not span across threads.

#### 5.4.3 Extension Sessions

`@ExtensionSession` annotation automatically pushes an available business scenario into the context based on certain
rules:

| `@ExtensionSession.value` | Description                                                            |
|---------------------------|------------------------------------------------------------------------|
| FIRST                     | Use the first `BizScenarioParam`-type parameter                        |
| LAST                      | Use the last `BizScenarioParam`-type parameter                         |
| SPECIFIED                 | Use the parameter specified by `@RouteBy` (must be `BizScenarioParam`) |
| CUSTOM                    | Use `@ExtensionSession.customResolver` to parse from all parameters    |
| IGNORE                    | Do not modify the context                                              |
| AUTO (default)            | Tries `CUSTOM` → `SPECIFIED` → `FIRST`, and falls back to `IGNORE`     |

`@ExtensionSession` is typically used at the API layer to populate the context at the entry point. This allows
extensible interfaces to be called without explicitly passing `BizScenarioParam` parameters, reducing code intrusion.
For example:

```java

@API
@ExtensionSession
public class BizApiImpl implements BizApi {
    @Autowired
    private BizAbility bizAbility;

    // Suppose BizParam implements BizScenarioParam
    public Result<BizData> getData(BizParam param) {
        // Even without extra parameters, extensible interfaces can route correctly
        bizAbility.runWithoutParam();
    }
}
```

---

## 6. Logging

Co-Framework offers powerful logging features for tracking and managing service calls and business processes.

### 6.1 Enabling Logging

In addition to enabling invocation logs via the `@API` annotation at the API layer, you can also enable and configure
invocation logging on any service using the `@Logging` annotation:

```java

@Logging(
        // Log name, defaults to the current class name
        name = "business",
        // Whether to log parameters (default: true). If argKvs is set, args won't be logged directly.
        logArgs = true,
        // Whether to log return values (default: true)
        logResult = true,
        // Whether to log stack traces (default: true)
        logException = true,
        // A SpEL expression to determine success of the call. Usually not needed if the result model is compatible with ApiResult.
        expSuccess = "#r?.success",
        // SpEL expression for error code. Usually not needed if the result or exception model is compatible.
        expCode = "#r?.errorCode",
        // SpEL expression for error message. Usually not needed if the result model is compatible.
        expMessage = "#r?.errorMessage",
        // Key-value pairs for important parameters. Key is String, value is a SpEL expression.
        argKvs = {"bizScenario", "#a0?.bizScenario",
                "userId", "#a0?.userId"}
)
public Result<BizData> run(BizParam param) {
    // ...
}
```

### 6.2 Log Format

Logs are recorded in the following format:

```json5
{
  // ERROR if an exception is thrown, WARN if failed, otherwise INFO
  "level": "WARN",
  "method": "BizService.run",
  "success": false,
  "code": "ERROR_CODE",
  "message": "error message",
  "elapsed": 0,
  "args": {
    "bizScenario": "*|*",
    "userId": 10000
  },
  // Recorded if there is a return value
  "result": {
    "status": "ENABLED"
  },
  // Recorded if an exception is thrown
  "exception": "IllegalArgumentException: id is null"
}
```

Note: To enable JSON-format logs, ensure that `jackson-databind` is on the classpath and that `LogUtils.logAsJson` is
`true` (which is the default).

---

We hope that Co-Framework will become a valuable assistant in your development process, helping your business system run
more efficiently and reliably.