package org.rkoubsky;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "orders.service.client")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class OrderServiceConfig {

    private String hostname;
    private int port;
    private String url;
    @ToString.Exclude
    private String username;
    @ToString.Exclude
    private String password;
}
