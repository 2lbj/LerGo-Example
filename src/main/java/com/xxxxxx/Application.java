package com.xxxxxx;

import com.lergo.framework.LergoBootStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application extends LergoBootStarter {
    public static void main(String[] args) {
        System.out.printf("\n\n\t\t\tSwagger-UI: http://localhost:%s%n \n\n",
            SpringApplication.run(Application.class, args)
                .getEnvironment().getProperty("server.port"));
    }
}