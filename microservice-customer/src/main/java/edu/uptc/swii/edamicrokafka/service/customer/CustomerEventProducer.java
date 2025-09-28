package edu.uptc.swii.edamicrokafka.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class CustomerEventProducer {
  private static final String TOPIC_ADD = "addcustomer_events";
  private static final String TOPIC_EDIT = "editcustomer_events";
  private static final String TOPIC_DELETE = "deletecustomer_events";
  private static final String TOPIC_ADD_LOGIN = "addlogin_events";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateAdd;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateAddLogin;

  public void sendAddCustomerEvent(Customer customer, String password) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateAdd.send(TOPIC_ADD, json);
    sendAddLoginEvent(customer, password);
  }

  public void sendAddLoginEvent(Customer customer, String password) {
    String loginJson = "{\"customerId\":\"" + customer.getDocument() + "\",\"password\":\"" + password + "\"}";
    kafkaTemplateAddLogin.send(TOPIC_ADD_LOGIN, loginJson);
  }

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateEdit;

  public void sendEditCustomerEvent(Customer customer) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateEdit.send(TOPIC_EDIT, json);
  }

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateDelete;

  public void sendDeleteCustomerEvent(Customer customer) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateDelete.send(TOPIC_DELETE, json);
  }
}
