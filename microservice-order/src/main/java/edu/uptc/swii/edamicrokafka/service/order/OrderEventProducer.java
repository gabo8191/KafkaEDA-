package edu.uptc.swii.edamicrokafka.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class OrderEventProducer {
  private static final String TOPIC_ADD = "addorder_events";
  private static final String TOPIC_EDIT = "editorder_events";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateAdd;

  public void sendAddOrderEvent(Order order) {
    String json = JsonUtils.toJson(order);
    kafkaTemplateAdd.send(TOPIC_ADD, json);
  }

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateEdit;

  public void sendEditOrderEvent(Order order) {
    String json = JsonUtils.toJson(order);
    kafkaTemplateEdit.send(TOPIC_EDIT, json);
  }
}
