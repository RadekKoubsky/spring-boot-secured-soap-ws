package org.rkoubsky.client;

import com.google.common.collect.Lists;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.rkoubsky.OrderServiceConfig;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@Component
public class OrdersWebTemplate {
    public static final int CONNECT_TIMEOUT_MILLISECONDS = 10_000;

    private final OrderServiceConfig serviceConfig;

    @Inject
    public OrdersWebTemplate(final OrderServiceConfig serviceConfig) {
        this.serviceConfig = serviceConfig;
    }

    public Jaxb2Marshaller jaxb2Marshaller() {
        final Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        jaxb2Marshaller.setContextPath("com.kouba67.orders.types");

        return jaxb2Marshaller;
    }

    public HttpClient httpClientPreemptiveAuth() {
        return HttpClientBuilder.create()
                                .setDefaultRequestConfig(this.requestConfig())
                                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                                .setDefaultHeaders(this.defaultHeaders())
                                .build();
    }

    public HttpClient httpClient() {
        return HttpClientBuilder.create()
                                .setDefaultRequestConfig(this.requestConfig())
                                .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                                .setDefaultCredentialsProvider(this.credentialsProvider())
                                .build();
    }

    private Collection<? extends Header> defaultHeaders() {
        final String auth = this.serviceConfig.getUsername() + ":" + this.serviceConfig.getPassword();
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        final String authHeader = "Basic " + new String(encodedAuth);

        final Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, authHeader);
        final List<Header> headers = Lists.newArrayList(header);
        return Collections.unmodifiableCollection(headers);
    }

    private RequestConfig requestConfig() {
        return RequestConfig.custom()
                            .setConnectTimeout(CONNECT_TIMEOUT_MILLISECONDS)
                            .setSocketTimeout(CONNECT_TIMEOUT_MILLISECONDS)
                            .build();
    }

    public CredentialsProvider credentialsProvider() {
        final BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, this.usernamePasswordCredentials());
        return credentialsProvider;
    }

    public UsernamePasswordCredentials usernamePasswordCredentials() {
        return new UsernamePasswordCredentials(this.serviceConfig.getUsername(),
                                               this.serviceConfig.getPassword());
    }
}
