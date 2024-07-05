package com.example.crash.config;

import com.example.crash.model.coinbase.PriceResponse;
import com.example.crash.model.crashsession.CrashSessionCategory;
import com.example.crash.model.crashsession.CrashSessionCreateRequestBody;
import com.example.crash.model.speaker.SessionSpeaker;
import com.example.crash.model.speaker.SessionSpeakerCreateRequestBody;
import com.example.crash.model.user.UserSignupRequestBody;
import com.example.crash.service.CrashSessionService;
import com.example.crash.service.SessionSpeakerService;
import com.example.crash.service.UserService;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

@Configuration
public class ApplicationConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
  private static final RestClient restClient = RestClient.create();

  private static final Faker faker = new Faker();

  private final UserService userService;
  private final SessionSpeakerService sessionSpeakerService;
  private final CrashSessionService crashSessionService;

  public ApplicationConfiguration(UserService userService,
      SessionSpeakerService sessionSpeakerService,
      CrashSessionService crashSessionService) {
    this.userService = userService;
    this.sessionSpeakerService = sessionSpeakerService;
    this.crashSessionService = crashSessionService;
  }


  @Bean
  public ApplicationRunner applicationRunner() {
    return args -> {

//      createTestUsers();
//      createTestSessionSpeakers(10);

      // TODO: Bitcoin 가격 조회
//      getBitcoinUsdPrice();

    };
  }

  private Double getBitcoinUsdPrice() {
    PriceResponse response = restClient
        .get()
        .uri("https://api.coinbase.com/v2/prices/BTC-USD/buy")
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,
            (request, response1) -> {
              logger.error(new String(response1.getBody().readAllBytes(), StandardCharsets.UTF_8));
            })
        .body(PriceResponse.class);

    logger.info(response.toString());

    return Double.parseDouble(response.data().amount());
  }


  private void createTestSessionSpeakers(int numberOfSpeakers) {
    List<SessionSpeaker> sessionSpeakers = IntStream.range(0, numberOfSpeakers)
        .mapToObj(i -> createTestSessionSpeaker()).toList();

    sessionSpeakers
        .forEach(
            sessionSpeaker -> {
              int numberOfSessions = new Random().nextInt(4) + 1;
              IntStream.range(0, numberOfSessions)
                  .forEach(i -> createTestCrashSession(sessionSpeaker));
            }
        );

  }

  private SessionSpeaker createTestSessionSpeaker() {
    String name = faker.name().fullName();
    String company = faker.company().name();
    String description = faker.shakespeare().hamletQuote();

    SessionSpeakerCreateRequestBody requestBody = new SessionSpeakerCreateRequestBody(
        company, name, description);

    return sessionSpeakerService.createSessionSpeaker(requestBody);
  }

  private void createTestCrashSession(SessionSpeaker sessionSpeaker) {
    String title = faker.book().title();
    String body =
        faker.shakespeare().romeoAndJulietQuote() +
            faker.shakespeare().hamletQuote() +
            faker.shakespeare().kingRichardIIIQuote() +
            faker.shakespeare().asYouLikeItQuote();

    crashSessionService.createCrashSession(
        new CrashSessionCreateRequestBody(
            title,
            body,
            getRandomCategory(),
            ZonedDateTime.now().plusDays(new Random().nextInt(2) + 1),
            sessionSpeaker.speakerId()
        ));
  }

  private CrashSessionCategory getRandomCategory() {
    CrashSessionCategory[] categories = CrashSessionCategory.values();
    return categories[new Random().nextInt(categories.length)];
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
