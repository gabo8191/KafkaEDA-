package edu.uptc.swii.edamicrokafka.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import edu.uptc.swii.edamicrokafka.model.Login;
import edu.uptc.swii.edamicrokafka.utils.JsonUtils;

@Service
public class LoginEventProducer {
    private static final String TOPIC_ADD = "addlogin_events";
    private static final String TOPIC_EDIT = "editlogin_events";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateAdd;

    public void sendAddLoginEvent(Login login) {
        String json = JsonUtils.toJson(login);
        kafkaTemplateAdd.send(TOPIC_ADD, json);
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplateEdit;

    public void sendEditLoginEvent(Login login) {
        String json = JsonUtils.toJson(login);
        kafkaTemplateEdit.send(TOPIC_EDIT, json);
    }
}
