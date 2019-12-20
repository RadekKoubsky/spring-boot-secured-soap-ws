package org.rkoubsky.ws;

import com.kouba67.orders.types.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@Endpoint
@Slf4j
public class OrdersWs {

    public static final String ORDERS_NAMESPACE = "http://kouba67.com/orders/types";
    public static final String ORDERS_LOCAL_PART = "order";
    public static final String ORDERS_SOAP_ACTION = "http://kouba67.com/orders/service/sendOrder";

    @PayloadRoot(namespace = ORDERS_NAMESPACE, localPart = ORDERS_LOCAL_PART)
    @SoapAction(value = ORDERS_SOAP_ACTION)
    @ResponsePayload
    public void processDTS(@RequestPayload final Order order) {
        log.info("Received order with id:{} and item:{}", order.getOrderId(), order.getItem());
    }
}
