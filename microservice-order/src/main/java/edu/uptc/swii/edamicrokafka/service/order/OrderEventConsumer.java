package edu.uptc.swii.edamicrokafka.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class OrderEventConsumer {
  private static final String TOPIC_ORDER = "order_events";

  @Autowired
  private OrderService orderService;

  @KafkaListener(topics = TOPIC_ORDER, groupId = "order_group")
  public void handleOrderEvent(String orderJson) {
    Order order = JsonUtils.fromJson(orderJson, Order.class);
    orderService.save(order);
  }
}
