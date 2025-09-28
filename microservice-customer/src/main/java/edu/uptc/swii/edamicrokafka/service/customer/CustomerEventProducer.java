package edu.uptc.swii.edamicrokafka.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class CustomerEventProducer {
  private static final String TOPIC_CUSTOMER = "customer_events";
  private static final String TOPIC_LOGIN = "login_events";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateCustomer;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateLogin;

  public void sendAddCustomerEvent(Customer customer, String password) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateCustomer.send(TOPIC_CUSTOMER, json);
    sendAddLoginEvent(customer, password);
  }

  public void sendEditCustomerEvent(Customer customer) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateCustomer.send(TOPIC_CUSTOMER, json);
  }

  public void sendDeleteCustomerEvent(Customer customer) {
    String json = JsonUtils.toJson(customer);
    kafkaTemplateCustomer.send(TOPIC_CUSTOMER, json);
  }

  private void sendAddLoginEvent(Customer customer, String password) {
    String loginJson = "{\"customerId\":\"" + customer.getDocument() + "\",\"password\":\"" + password + "\"}";
    kafkaTemplateLogin.send(TOPIC_LOGIN, loginJson);
  }
}
