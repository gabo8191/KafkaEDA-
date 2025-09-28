package edu.uptc.swii.edamicrokafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class EdamicrokafkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(EdamicrokafkaApplication.class, args);
  }

}
