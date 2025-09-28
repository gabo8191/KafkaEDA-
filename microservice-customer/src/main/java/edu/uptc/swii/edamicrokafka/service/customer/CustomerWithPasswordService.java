package edu.uptc.swii.edamicrokafka.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.dto.CustomerWithPasswordRequest;
import edu.uptc.swii.edamicrokafka.model.Customer;

@Service
public class CustomerWithPasswordService {

  @Autowired
  private CustomerEventProducer customerEventProducer;

  public void createCustomerWithPassword(CustomerWithPasswordRequest request) {
    Customer customer = new Customer();
    customer.setDocument(request.getDocument());
    customer.setFirstname(request.getFirstname());
    customer.setLastname(request.getLastname());
    customer.setAddress(request.getAddress());
    customer.setPhone(request.getPhone());
    customer.setEmail(request.getEmail());

    customerEventProducer.sendAddCustomerEvent(customer, request.getPassword());
  }
}
