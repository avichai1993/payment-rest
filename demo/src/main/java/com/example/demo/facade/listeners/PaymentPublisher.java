package com.example.demo.facade.listeners;

import com.example.demo.business.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentPublisher {
    private RabbitTemplate rabbitTemplate;
    private Queue queue;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public PaymentPublisher(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void publish(Payment payment) {
        rabbitTemplate.convertAndSend(queue.getName(), payment);
        logger.info("sent payment to risk engine: " + payment);
    }
}
