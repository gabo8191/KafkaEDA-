package edu.uptc.swii.edamicrokafka.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class LoginEventProducer {
  private static final String TOPIC_LOGIN = "login_events";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplateLogin;

  public void sendAddLoginEvent(Login login) {
    String json = JsonUtils.toJson(login);
    kafkaTemplateLogin.send(TOPIC_LOGIN, json);
  }

  public void sendEditLoginEvent(Login login) {
    String json = JsonUtils.toJson(login);
    kafkaTemplateLogin.send(TOPIC_LOGIN, json);
  }
}
