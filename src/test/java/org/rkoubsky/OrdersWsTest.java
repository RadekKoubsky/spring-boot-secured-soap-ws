package org.rkoubsky;

import com.kouba67.orders.types.ObjectFactory;
import com.kouba67.orders.types.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rkoubsky.client.customhttp.nosenderbean.CustomClientNoBean;
import org.rkoubsky.client.customhttp.withsenderbean.CustomClientWithBean;
import org.rkoubsky.client.embeddedhttp.nosenderbean.EmbeddedClientNoBean;
import org.rkoubsky.client.embeddedhttp.withsenderbean.EmbeddedClientWithBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

/**
 * @author Radek Koubsky (radekkoubsky@gmail.com)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrdersWsTest {

    @Inject
    private CustomClientWithBean customClientWithBean;

    @Inject
    private CustomClientNoBean customClientNoBean;

    @Inject
    private EmbeddedClientWithBean embeddedClientWithBean;

    @Inject
    private EmbeddedClientNoBean embeddedClientNoBean;

    @Test
    public void whenCustomClientWithSenderBeanAndPreemptiveAuthThenAuthSuccess() throws Exception {
        this.customClientWithBean.sendOrder(this.order());
    }

    @Test
    public void whenCustomClientWithoutSenderBeanThenAuthSuccess() throws Exception {
        this.customClientNoBean.sendOrder(this.order());
    }

    @Test
    public void whenEmbeddedClientWithSenderBeanThenAuthSuccess() throws Exception {
        this.embeddedClientWithBean.sendOrder(this.order());
    }

    @Test
    public void whenEmbeddedClientWithoutSenderBeanThenAuthSuccess() throws Exception {
        this.embeddedClientNoBean.sendOrder(this.order());
    }

    private Order order(){
        final ObjectFactory objectFactory = new ObjectFactory();
        final Order order = objectFactory.createOrder();
        order.setOrderId("5");
        order.setItem("Lenovo ThinkPad");
        return order;
    }
}
