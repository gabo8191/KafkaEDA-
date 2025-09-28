package edu.uptc.swii.edamicrokafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/orders/{orderId}")
  public String getOrderById(@PathVariable Long orderId) {
    orderEventProducer.sendFindByOrderIDEvent(orderId);
    return "Order search event sent successfully";
  }

  @GetMapping("/orders")
  public String getAllOrders() {
    orderEventProducer.sendFindAllOrdersEvent("");
    return "All orders search event sent successfully";
  }
}
