package org.rkoubsky.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

import javax.servlet.Servlet;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@EnableWs
@Configuration
public class OrdersWsConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean<Servlet> messageDispatcherServlet(final ApplicationContext applicationContext) {
        final MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);

        return new ServletRegistrationBean<>(servlet, "/ws/order_service/*");
    }

    @Bean(name = "order_service")
    public Wsdl11Definition defaultWsdl11Definition() {
        final SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("/order_service.wsdl"));

        return wsdl11Definition;
    }
}
