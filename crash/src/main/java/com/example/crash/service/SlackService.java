package com.example.crash.service;

import com.example.crash.model.registration.Registration;
import com.example.crash.model.slack.Block;
import com.example.crash.model.slack.BlockText;
import com.example.crash.model.slack.SlackNotificationMessage;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SlackService {

  private static final Logger logger = LoggerFactory.getLogger(SlackService.class);
  private static final RestClient restClient = RestClient.create();

  public void sendSlackNotification(Registration registration) {

    String response = restClient
        .post()
        .uri("https://hooks.slack.com/services/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")
        .header("Content-Type", "text/plain")
        .header("Authorization", "Bearer " + System.getenv("SLACK_TOKEN"))
        .body(getSlackMessageBody(registration))
        .retrieve()
        .body(String.class);

    logger.info(response);
  }

  private SlackNotificationMessage getSlackMessageBody(Registration registration) {
    BlockText blockText = new BlockText("mrkdwn", getRegistrationLinkText(registration));
    Block slackBlock = new Block("section", blockText);

    return new SlackNotificationMessage(List.of(slackBlock));
  }

  private String getRegistrationLinkText(Registration registration) {
    var baseLink = "https://dev-jayce.github.io/crash/registration.html?registration=";
    var registrationId = registration.registrationId();
    var username = registration.user().username();
    var sessionId = registration.crashSession().sessionId();
    var link = baseLink + registrationId + "," + username + "," + sessionId;

    return ":collistion: *CRASH* <" + link + "|Go to registration details>";
  }

}
