package edu.uptc.swii.edamicrokafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.service.CustomerEventProducer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@RestController
public class CustomerController {
  @Autowired
  private CustomerEventProducer customerEventProducer;

  @PostMapping("/addcustomer")
  public String sendMessageAddCustomer(@RequestBody String customer) {
    Customer customerObj = JsonUtils.fromJson(customer, Customer.class);
    customerEventProducer.sendAddCustomerEvent(customerObj);
    return "Add customer event sent successfully";
  }

  @PostMapping("/editcustomer")
  public String sendMessageEditCustomer(@RequestBody String customer) {
    Customer customerObj = JsonUtils.fromJson(customer, Customer.class);
    customerEventProducer.sendEditCustomerEvent(customerObj);
    return "Edit customer event sent successfully";
  }
}
