package com.xxxxxx;

import com.lergo.framework.LergoBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends LergoBootStarter {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(Application.class);
//        springApplication.setDefaultProperties(
//                MapUtil.of("spring.config.additional-location", "classpath:application.yaml"));
        System.out.printf("\n\t\tSwagger-UI: http://localhost:%s%n \n",
                springApplication.run(args)
                        .getEnvironment().getProperty("server.port"));
    }
}