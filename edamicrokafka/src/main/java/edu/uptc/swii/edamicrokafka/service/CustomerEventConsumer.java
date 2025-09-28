package edu.uptc.swii.edamicrokafka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class CustomerEventConsumer {
  private static final String TOPIC_ADD = "addcustomer_events";
  private static final String TOPIC_EDIT = "editcustomer_events";
  private static final String TOPIC_FINDBYID = "findcustomerbyid_events";
  private static final String TOPIC_FINDALLCUSTOMERS = "findallcustomers_events";

  @Autowired
  private CustomerService customerService;

  @KafkaListener(topics = TOPIC_ADD, groupId = "customer_group")
  public void handleAddCustomerEvent(String customer) {
    Customer receiveAddCustomer = JsonUtils.fromJson(customer, Customer.class);
    customerService.save(receiveAddCustomer);
  }

  @KafkaListener(topics = TOPIC_EDIT, groupId = "customer_group")
  public void handleEditCustomerEvent(String customer) {
    Customer receiveEditCustomer = JsonUtils.fromJson(customer, Customer.class);
    customerService.save(receiveEditCustomer);
  }

  @KafkaListener(topics = TOPIC_FINDBYID, groupId = "customer_group")
  public Customer handleFindCustomerByIDEvent(String document) {
    Customer customerReceived = customerService.findById(document);
    return customerReceived;
  }

  @KafkaListener(topics = TOPIC_FINDALLCUSTOMERS, groupId = "customer_group")
  public List<Customer> handleFindAllCustomers() {
    List<Customer> customersReceived = customerService.findAll();
    return customersReceived;
  }
}
