package edu.uptc.swii.edamicrokafka.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order")
public class Order {
  @Id
  @Column(name = "orderid")
  private Long orderId;

  @Column(name = "customerid")
  private String customerId;

  @Column(name = "status")
  private String status;

  @ManyToOne
  @JoinColumn(name = "customerid", referencedColumnName = "document", insertable = false, updatable = false)
  private Customer customer;
}
