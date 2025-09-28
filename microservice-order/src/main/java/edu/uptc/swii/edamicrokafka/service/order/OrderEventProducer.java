package edu.uptc.swii.edamicrokafka.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class OrderEventProducer {
  private static final String TOPIC_ORDER = "order_events";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateOrder;

  public void sendAddOrderEvent(Order order) {
    String json = JsonUtils.toJson(order);
    kafkaTemplateOrder.send(TOPIC_ORDER, json);
  }

  public void sendEditOrderEvent(Order order) {
    String json = JsonUtils.toJson(order);
    kafkaTemplateOrder.send(TOPIC_ORDER, json);
  }
}
