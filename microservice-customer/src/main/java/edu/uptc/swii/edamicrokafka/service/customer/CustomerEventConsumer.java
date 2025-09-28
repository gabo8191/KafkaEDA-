package edu.uptc.swii.edamicrokafka.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class CustomerEventConsumer {
  private static final String TOPIC_CUSTOMER = "customer_events";

  @Autowired
  private CustomerService customerService;

  @KafkaListener(topics = TOPIC_CUSTOMER, groupId = "customer_group")
  public void handleCustomerEvent(String customerJson) {
    Customer customer = JsonUtils.fromJson(customerJson, Customer.class);

    if (customer.getDocument() != null && !customer.getDocument().isEmpty()) {
      customerService.save(customer);
    } else {
      customerService.delete(customer);
    }
  }
}
