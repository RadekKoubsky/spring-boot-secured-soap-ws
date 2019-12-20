package org.rkoubsky.client.customhttp.nosenderbean;

import com.kouba67.orders.types.Order;
import lombok.extern.slf4j.Slf4j;
import org.rkoubsky.ws.OrdersWs;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.inject.Inject;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@Component
@Slf4j
public class CustomClientNoBean {
    private final WebServiceTemplate webServiceTemplate;

    @Inject
    public CustomClientNoBean(final @CustomNoSenderBean WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public void sendOrder(final Order order) {
        this.webServiceTemplate.marshalSendAndReceive(order, new SoapActionCallback(OrdersWs.ORDERS_SOAP_ACTION));
        log.info("Order with id:{} and item:{} has been successfully sent.", order.getOrderId(), order.getItem());
    }
}
