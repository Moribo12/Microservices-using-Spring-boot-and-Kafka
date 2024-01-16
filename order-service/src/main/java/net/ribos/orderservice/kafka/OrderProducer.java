package net.ribos.orderservice.kafka;

import net.ribos.basedomains.dto.OrderEvent;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private static final Logger LOGGER= LoggerFactory.getLogger(OrderProducer.class);
   @Value("${spring.kafka.topic.name}")
    private String topic;
    private final KafkaTemplate<String,OrderEvent> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


   public void sendMessage(OrderEvent event){
        LOGGER.info(String.format("Order event => %s", event.toString()));

        //create a message
       Message<OrderEvent> message = MessageBuilder
               .withPayload(event)
               .setHeader(KafkaHeaders.TOPIC, topic)
               .build();
       kafkaTemplate.send(message);
   }
}
