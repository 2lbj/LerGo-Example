package com.xxxxxx;

import com.lergo.framework.LergoBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends LergoBootStarter {

    /*
     * WebApplicationType.NONE 启动方式 (不加载任何web容器, 适用于定时任务等场景)
     */
//    public static void main(String[] args) {
//        //SpringApplication.run(Application.class, args);
//        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Application.class);
//        springApplicationBuilder.web(WebApplicationType.NONE);
//        springApplicationBuilder.run(args);
//    }


/*
  WebApplicationType.SERVLET/REACTIVE 启动方式 (显式加载web容器, 适用于web项目)
 */
//    public static void main(String[] args) {
//        // 显式指定使用REACTIVE类型，尽管Spring Boot会自动检测
//        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(Application.class);
//        springApplicationBuilder.web(WebApplicationType.REACTIVE);
//        springApplicationBuilder.run(args);
//    }
//
//    // 可选的定制Netty服务器配置，虽然默认配置通常已足够
//    @Bean
//    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> nettyCustomizer() {
//        return factory -> {
//            // 这里可以添加自定义的Netty服务器配置
//        };
//    }
//
//    // 定义一个简单的路由
//    @Bean
//    public RouterFunction<ServerResponse> route() {
//        return RouterFunctions.route(GET("/hello"),
//                req -> ServerResponse.ok().bodyValue("Hello, Reactive World with Spring WebFlux and Netty!"));
//    }
    
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        //强制加载配置文件
        //springApplication.setDefaultProperties(
        //        MapUtil.of("spring.config.additional-location", "classpath:application.yaml"));
        System.out.printf("\n\t\tSwagger-UI: http://localhost:%s%n \n",
                springApplication.run(args)
                        .getEnvironment().getProperty("server.port"));
    }

}