package net.ribos.orderservice.controller;

import net.ribos.basedomains.dto.Order;
import net.ribos.basedomains.dto.OrderEvent;
import net.ribos.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    //Constructor based Dependency Injection
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){

        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("order status is in pending stage");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);

        return  "Order placed successfully...";
    }
}
