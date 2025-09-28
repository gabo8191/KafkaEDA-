package edu.uptc.swii.edamicrokafka.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class LoginEventConsumer {
  private static final String TOPIC_LOGIN = "login_events";

  @Autowired
  private LoginService loginService;

  @KafkaListener(topics = TOPIC_LOGIN, groupId = "login_group")
  public void handleLoginEvent(String loginJson) {
    Login login = JsonUtils.fromJson(loginJson, Login.class);
    loginService.save(login);
  }
}
