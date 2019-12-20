package org.rkoubsky.client.embeddedhttp.nosenderbean;

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
public class EmbeddedConfigNoSenderBean {
    private final OrderServiceConfig serviceConfig;
    private final OrdersWebTemplate ordersWebTemplate;

    @Inject
    public EmbeddedConfigNoSenderBean(final OrderServiceConfig serviceConfig,
            final OrdersWebTemplate ordersWebTemplate) {
        this.serviceConfig = serviceConfig;
        this.ordersWebTemplate = ordersWebTemplate;
    }

    @Bean
    @EmbeddedNoSenderBean
    public WebServiceTemplate embeddedNoSenderBeanTemplate() throws Exception {
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

    public WebServiceMessageSender withEmbeddedClient() throws Exception {
        final HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
        sender.setCredentials(this.ordersWebTemplate.usernamePasswordCredentials());
        /**
         * Because the HttpComponentsMessageSender is not created as a Spring Bean (this method is
         * not annotated with @Bean annotation) and we did not pass a custom Http Client to constructor (it uses
         * a DefaultHttpClient internally), we must call afterPropertiesSet() method
         * on the HttpComponentsMessageSender instance to set the credentials, otherwise the credentials will not
         * be set and authentication will fail.
         */
        sender.afterPropertiesSet();
        return sender;
    }
}
