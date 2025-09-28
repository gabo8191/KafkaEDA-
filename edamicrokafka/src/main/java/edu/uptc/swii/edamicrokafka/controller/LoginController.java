package edu.uptc.swii.edamicrokafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.service.login.LoginEventProducer;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@RestController
public class LoginController {
  @Autowired
  private LoginEventProducer loginEventProducer;

  @PostMapping("/logins")
  public String createLogin(@RequestBody String login) {
    Login loginObj = JsonUtils.fromJson(login, Login.class);
    loginEventProducer.sendAddLoginEvent(loginObj);
    return "Login created successfully";
  }

  @PutMapping("/logins")
  public String updateLogin(@RequestBody String login) {
    Login loginObj = JsonUtils.fromJson(login, Login.class);
    loginEventProducer.sendEditLoginEvent(loginObj);
    return "Login updated successfully";
  }
}
