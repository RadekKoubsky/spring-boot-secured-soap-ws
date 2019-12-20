package org.rkoubsky.client.embeddedhttp.withsenderbean;

import lombok.extern.slf4j.Slf4j;
import org.rkoubsky.OrderServiceConfig;
import org.rkoubsky.client.OrdersWebTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.inject.Inject;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@Configuration
@Slf4j
public class EmbeddedConfigWithSenderBean {
    private final OrderServiceConfig serviceConfig;
    private final OrdersWebTemplate ordersWebTemplate;

    @Inject
    public EmbeddedConfigWithSenderBean(final OrderServiceConfig serviceConfig,
            final OrdersWebTemplate ordersWebTemplate) {
        this.serviceConfig = serviceConfig;
        this.ordersWebTemplate = ordersWebTemplate;
    }

    @Bean
    @EmbeddedWithSenderBean
    public WebServiceTemplate embeddedWithSenderBeanTemplate() {
        log.info("Using the following orders service config: {}", this.serviceConfig);
        final WebServiceTemplate webServiceTemplate = new WebServiceTemplate(this.ordersWebTemplate.jaxb2Marshaller(),
                                                                             this.ordersWebTemplate.jaxb2Marshaller());
        webServiceTemplate.setDefaultUri(String.format("http://%s:%s/%s",
                                                       this.serviceConfig.getHostname(),
                                                       this.serviceConfig.getPort(),
                                                       this.serviceConfig.getUrl()));
        webServiceTemplate.setMessageSender(this.withEmbeddedClient());
        return webServiceTemplate;
    }

    @Bean
    public WebServiceMessageSender withEmbeddedClient() {
        final HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
        sender.setCredentials(this.ordersWebTemplate.usernamePasswordCredentials());
        return sender;
    }
}
