package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.configuration.NoCsrfSecurityConfiguration;
import it.poli.csrf.model.health.HealthModel;
import it.poli.csrf.model.loggers.LoggersDescriptorModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"insecure"})
@Import({NoCsrfSecurityConfiguration.class})
class CsrfReactiveApplicationInsecureTests {

  @Autowired(required = false)
  private ApplicationContext applicationContext;

  private WebTestClient webClient;

  @BeforeEach
  public void init() {
    webClient =
        WebTestClient.bindToApplicationContext(applicationContext).configureClient().build();
  }

  @Test
  void contextLoads() {
    assertNotNull(applicationContext, "null applicationContext");
  }

  @Test
  final void testHealthGet() {
    webClient
        .get()
        .uri("/actuator/health")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(HealthModel.class);
  }

  @Test
  final void testLogsGet() {
    webClient
        .get()
        .uri("/actuator/loggers")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(LoggersDescriptorModel.class);
  }
}
