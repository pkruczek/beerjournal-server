package com.beerjournal.infrastructure.error;

import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component
class SlackErrorSender {

    private final RestOperations restOperations;

    @Value("${slack.error-notifications.enabled:false}")
    private boolean notificationsEnabled;

    @Value("${slack.webhook}")
    private String slackWebhookUrl;

    private SlackErrorSender() {
        restOperations = new RestTemplate();
    }

    boolean sendErrorMsg(String msg) {
        if (!notificationsEnabled) {
            return false;
        }
        ResponseEntity<String> responseEntity =
                restOperations.postForEntity(slackWebhookUrl, createBody(msg), String.class);
        return isResponseOk(responseEntity);
    }

    private boolean isResponseOk(ResponseEntity<String> responseEntity) {
        return responseEntity.getStatusCodeValue() == 200;
    }

    private ImmutableMap<String, String> createBody(String msg) {
        return ImmutableMap.of("text", msg);
    }

}
