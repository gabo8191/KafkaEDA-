package edu.uptc.swii.edamicrokafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.service.order.OrderEventProducer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@RestController
public class OrderController {
  @Autowired
  private OrderEventProducer orderEventProducer;

  @PostMapping("/orders")
  public String createOrder(@RequestBody String order) {
    Order orderObj = JsonUtils.fromJson(order, Order.class);
    orderEventProducer.sendAddOrderEvent(orderObj);
    return "Order created successfully";
  }

  @PutMapping("/orders")
  public String updateOrder(@RequestBody String order) {
    Order orderObj = JsonUtils.fromJson(order, Order.class);
    orderEventProducer.sendEditOrderEvent(orderObj);
    return "Order updated successfully";
  }
}
