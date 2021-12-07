package com.example.carrot.rabbitMq;

import com.example.carrot.common.EcSapUrls;
import com.example.carrot.model.User;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MqTest {
    /**
     * 采购单逾期mq
     */
    @Bean
    public DirectExchange purchaseOrderDelayExchange(){
        return new DirectExchange("trade.purchase-order-delay.hj.exchange",true,false);
    }

    @Bean
    public Queue purchaseOrderDelayQueue(){
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", "trade.purchase-order-delay.hj.do.exchange");
        params.put("x-dead-letter-routing-key", "trade.purchase-order-delay.hj.do.routing-key");
        params.put("x-message-ttl", 1000);
        return new Queue("trade.purchase-order-delay.hj.queue", true, false, false, params);
    }

    @Bean
    public Binding purchaseOrderDelayBinding(){
        return BindingBuilder.bind(purchaseOrderDelayQueue())
                .to(purchaseOrderDelayExchange())
                .with("trade.purchase-order-delay.hj.routing-key");
    }

    /**
     * 采购单逾期死信mq
     */
    @Bean
    public DirectExchange purchaseOrderDelayDoExchange(){
        return new DirectExchange("trade.purchase-order-delay.hj.do.exchange",true,false);
    }

    @Bean
    public Queue purchaseOrderDelayDoQueue(){
        return new Queue("trade.purchase-order-delay.hj.do.queue", true);
    }

    @Bean
    public Binding purchaseOrderDelayDoBinding(){
        return BindingBuilder.bind(purchaseOrderDelayDoQueue())
                .to(purchaseOrderDelayDoExchange())
                .with("trade.purchase-order-delay.hj.do.routing-key");
    }
    @Value(value = "${spring.rabbitmq.listener.simple.max-concurrency}")
    private Integer retry;
    private Integer flag = 0;  //已重试次数
    @RabbitListener(queues = "trade.purchase-order-delay.hj.do.queue")
    public void purchaseOrderIsDelay(
            @Payload User id,
            @Headers Map<String, Object> properties,
            Channel channel) throws IOException {
        Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);
        try {
//        byte[] body = message.getBody();
//        log.info("message:{}",message);
//        log.info("body:{}",body);
//        String jsonStr = new String(body,"UTF-8");
//        log.info("start handle PurchaseOrder is delay event and body ={}", jsonStr);
//        JSONObject.parseObject();  可以把jsonStr转成对象
            log.info("start handle PurchaseOrder is delay event and body ={}", id);
            System.out.println("propertiesId----"+properties.get("id"));
            System.out.println("consumer success!");
            String spring_returned_message_correlation = (String)properties.get("spring_returned_message_correlation");
            System.out.println("consumer cor id-----"+spring_returned_message_correlation);
            System.out.println("delivery tag ---"+ deliveryTag);
            throw new RuntimeException("try");
//            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            flag ++ ;
            throw e;
        }
        finally {
            if (flag >= retry){
                System.out.println("ack it");
                channel.basicAck(deliveryTag,false);
                flag = 0;
            }
        }
    }
    @Bean
    public EcSapUrls ecSapUrls(){
        return new EcSapUrls();
    }
}
