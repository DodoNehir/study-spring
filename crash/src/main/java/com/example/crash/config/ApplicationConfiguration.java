package com.example.crash.config;

import com.example.crash.model.speaker.SessionSpeaker;
import com.example.crash.model.speaker.SessionSpeakerCreateRequestBody;
import com.example.crash.model.user.UserSignupRequestBody;
import com.example.crash.service.SessionSpeakerService;
import com.example.crash.service.UserService;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private static final Faker faker = new Faker();

  private final UserService userService;
  private final SessionSpeakerService sessionSpeakerService;

  public ApplicationConfiguration(UserService userService,
      SessionSpeakerService sessionSpeakerService) {
    this.userService = userService;
    this.sessionSpeakerService = sessionSpeakerService;
  }


  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {

      createTestUsers();
      createTestSessionSpeakers(10);

    };
  }


  private void createTestSessionSpeakers(int numberOfSpeakers) {
    IntStream.range(0, numberOfSpeakers).forEach(i -> createTestSessionSpeaker());
//    IntStream.range(0, numberOfSpeakers).mapToObj(i -> createTestSessionSpeaker()).toList();
  }

  private SessionSpeaker createTestSessionSpeaker() {
    String name = faker.name().fullName();
    String company = faker.company().name();
    String description = faker.shakespeare().hamletQuote();

    SessionSpeakerCreateRequestBody requestBody = new SessionSpeakerCreateRequestBody(
        company, name, description);

    return sessionSpeakerService.createSessionSpeaker(requestBody);
  }

  private void createTestUsers() {
    userService.signUp(new UserSignupRequestBody("jayce", "1234",
        "Dev Jayce", "jayce@crash.com"));
    userService.signUp(new UserSignupRequestBody(faker.name().name(), faker.compass().word(),
        faker.name().fullName(), faker.internet().emailAddress()));
    userService.signUp(new UserSignupRequestBody(faker.name().name(), faker.compass().word(),
        faker.name().fullName(), faker.internet().emailAddress()));
    userService.signUp(new UserSignupRequestBody(faker.name().name(), faker.compass().word(),
        faker.name().fullName(), faker.internet().emailAddress()));
  }

}
