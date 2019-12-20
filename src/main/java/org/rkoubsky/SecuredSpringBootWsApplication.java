package org.rkoubsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(OrderServiceConfig.class)
public class SecuredSpringBootWsApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SecuredSpringBootWsApplication.class, args);
    }
}
