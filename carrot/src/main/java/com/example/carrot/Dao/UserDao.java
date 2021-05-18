package com.example.carrot.Dao;

import com.example.carrot.common.MyBatisDao;
import com.example.carrot.model.User;

import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class UserDao extends MyBatisDao<User> {


    private final RabbitTemplate rabbitTemplate;

    public UserDao(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                System.out.println("corId---"+correlationData.getId());
            }
        });
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println("return---"+returnedMessage);
            }
        });
        this.rabbitTemplate = rabbitTemplate;
    }
    public List<User> findAll(){
        return getSqlSession().selectList(sqlId("findAll"));
    }

    public Boolean mq (Long id){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString().replace("-", "") + sdf.format(new Date()));
        System.out.println(correlationData.getId());
        User user = new User();
        user.setId(id);
        user.setUserName("666");
        rabbitTemplate.convertAndSend("trade.purchase-order-delay.hj.exchange","trade.purchase-order-delay.hj.routing-key"
                ,user,correlationData);
        return true;
    }
}
