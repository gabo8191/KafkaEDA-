package edu.uptc.swii.edamicrokafka.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class OrderEventConsumer {
  private static final String TOPIC_ADD = "addorder_events";
  private static final String TOPIC_EDIT = "editorder_events";

  @Autowired
  private OrderService orderService;

  @KafkaListener(topics = TOPIC_ADD, groupId = "order_group")
  public void handleAddOrderEvent(String order) {
    Order receiveAddOrder = JsonUtils.fromJson(order, Order.class);
    orderService.save(receiveAddOrder);
  }

  @KafkaListener(topics = TOPIC_EDIT, groupId = "order_group")
  public void handleEditOrderEvent(String order) {
    Order receiveEditOrder = JsonUtils.fromJson(order, Order.class);
    orderService.save(receiveEditOrder);
  }
}
