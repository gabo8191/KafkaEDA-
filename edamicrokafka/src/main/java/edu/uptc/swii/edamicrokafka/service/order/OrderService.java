package edu.uptc.swii.edamicrokafka.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Order;
import edu.uptc.swii.edamicrokafka.repository.OrderRepository;

@Service
public class OrderService {

  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public boolean save(Order order) {
    boolean flag = false;
    Order processOrder = orderRepository.saveAndFlush(order);
    if (processOrder != null) {
      flag = true;
    }
    return flag;
  }

  public boolean delete(Order order) {
    boolean flag = false;
    try {
      orderRepository.delete(order);
      flag = true;
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return flag;
  }

  public Order findById(Long orderId) {
    Order order = null;
    Optional<Order> optionalOrder = orderRepository.findById(orderId);
    if (optionalOrder.isPresent())
      order = optionalOrder.get();
    return order;
  }

  public List<Order> findAll() {
    List<Order> listOrder = new ArrayList<Order>();
    Iterable<Order> orders = orderRepository.findAll();
    orders.forEach((o) -> {
      listOrder.add(o);
    });
    return listOrder;
  }
}
