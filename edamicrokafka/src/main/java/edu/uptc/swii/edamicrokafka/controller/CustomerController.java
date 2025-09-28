package edu.uptc.swii.edamicrokafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.edamicrokafka.model.Customer;
import edu.uptc.swii.edamicrokafka.service.customer.CustomerEventProducer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@RestController
public class CustomerController {
  @Autowired
  private CustomerEventProducer customerEventProducer;

  @PostMapping("/customers")
  public String createCustomer(@RequestBody String customer) {
    Customer customerObj = JsonUtils.fromJson(customer, Customer.class);
    customerEventProducer.sendAddCustomerEvent(customerObj);
    return "Customer created successfully";
  }

  @PutMapping("/customers")
  public String updateCustomer(@RequestBody String customer) {
    Customer customerObj = JsonUtils.fromJson(customer, Customer.class);
    customerEventProducer.sendEditCustomerEvent(customerObj);
    return "Customer updated successfully";
  }

  @DeleteMapping("/customers/{document}")
  public String deleteCustomer(@PathVariable String document) {
    Customer customerObj = new Customer();
    customerObj.setDocument(document);
    customerEventProducer.sendDeleteCustomerEvent(customerObj);
    return "Customer deleted successfully";
  }

  @GetMapping("/customers/{document}")
  public String getCustomerById(@PathVariable String document) {
    customerEventProducer.sendFindByCustomerIDEvent(document);
    return "Customer search event sent successfully";
  }

  @GetMapping("/customers")
  public String getAllCustomers() {
    customerEventProducer.sendFindAllCustomersEvent("");
    return "All customers search event sent successfully";
  }
}
