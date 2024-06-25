[![Author](https://img.shields.io/badge/Author-hexLi-666699)](https://2lbj.github.io/)
[![last-commit](https://img.shields.io/github/last-commit/2lbj/lergo-spring-boot-starter)](https://github.com/2lbj/lergo-spring-boot-starter)
[![license](https://img.shields.io/badge/license-Apache%202.0-lime)](./LICENSE)

# LerGo-Example (模板工程)

> 众所周知 [Lergo](https://github.com/2lbj/lergo-spring-boot-starter) 作为一款简单易用的开发脚手架,
> 有着非常优秀的开发体验  
> 但是对于初次接触的开发者来说, 有时候会有一些不知所措的地方  
> 本项目旨在帮助开发者快速上手 Lergo 框架, 以及提供一些最佳实践

### 快速开始

<kbd>**3-1**</kbd> 创建一个maven项目 并引入依赖 注意`spring-boot-starter-parent`版本号与`LerGo`框架关系

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <groupId>io.github.2lbj</groupId>
    <artifactId>lergo-mini</artifactId>
    <version>1.0</version>
    <name>Mini</name>
    <description>LerGo mini project</description>

    <properties>
        <application.artifactId>${artifactId}</application.artifactId>
        <application.version>${version}</application.version>
        <application.name>${name}</application.name>
        <java.version>17</java.version>
    </properties>

    <dependencies>

        .....

        <!-- 引入核心脚手架 -->
        <dependency>
            <groupId>io.github.2lbj</groupId>
            <artifactId>lergo-spring-boot3-starter</artifactId>
            <version>1.1.9</version>
        </dependency>

        <!--引入本地jar包-->


    </dependencies>

    <build>

        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

<kbd>**3-2**</kbd> 创建配置文件 `application-dev.yml`

```yaml
spring:
  application:
    name: @application.name@
    artifactId: @project.artifactId@
    version: @project.version@
    description: @project.description@

```

<kbd>**3-3**</kbd> 创建启动类 `Application.java`

```java
import com.lergo.framework.LergoBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends LergoBootStarter {
    public static void main(String[] args) {
        System.out.printf("\n\t\tSwagger-UI: http://localhost:%s%n \n",
                SpringApplication.run(Application.class, args)
                        .getEnvironment().getProperty("server.port"));
    }
}
```

好了, 你已经完成了一个最简单的LerGo项目的创建, 你可以直接运行 `Application.java` 来启动项目
并查看日志启动成功输出的链接访问`Swagger`文档
> 为避免和现有端口冲突`LerGo`默认使用`9999`端口, 你可以在 `application-dev.yml` 中修改端口号

然后我们可以试着创建一个简单的接口类`DemoController.java`来测试一下

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DemoController {

    @Value("${spring.application.description}")
    String description;

    @RequestMapping(value = "/")
    public String test() {
        return "Minimal Spring Boot Application [" + description + "]";
    }

}
```

再次启动项目, 访问 `http://localhost:9999/` 就可以看到我们的接口返回了一段文本

### 高级配置

希望了解更多 `LerGo` 的使用方法, 请克隆本项目并查看源码

上述提到的工程可以在 [LerGo-Mini](docs/LerGo-Mini) 中找到

### [开发规范](RULES.md)

# 技术文章

* [框架LerGo选型的Webflux有什么技术特点](/docs/WebFlux/index.md)
* [什么是JWT以及Auth2.0认证机制](/docs/JWT_Auth2.0/index.md)
* [MyBatis-Flex 和同类框架对比](https://mybatis-flex.com/zh/intro/comparison.html)
