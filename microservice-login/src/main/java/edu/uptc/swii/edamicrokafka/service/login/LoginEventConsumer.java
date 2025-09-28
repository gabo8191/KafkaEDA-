package edu.uptc.swii.edamicrokafka.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class LoginEventConsumer {
    private static final String TOPIC_ADD = "addlogin_events";
    private static final String TOPIC_EDIT = "editlogin_events";

    @Autowired
    private LoginService loginService;

    @KafkaListener(topics = TOPIC_ADD, groupId = "login_group")
    public void handleAddLoginEvent(String login) {
        Login receiveAddLogin = JsonUtils.fromJson(login, Login.class);
        loginService.save(receiveAddLogin);
    }

    @KafkaListener(topics = TOPIC_EDIT, groupId = "login_group")
    public void handleEditLoginEvent(String login) {
        Login receiveEditLogin = JsonUtils.fromJson(login, Login.class);
        loginService.save(receiveEditLogin);
    }
}
