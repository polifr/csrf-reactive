package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
@ActiveProfiles(profiles = {"secure"})
@Import({CsrfSecurityConfiguration.class})
class CsrfReactiveApplicationSecureTests {

  @Autowired(required = false)
  private ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertNotNull(applicationContext, "null applicationContext");
  }

  @Test
  final void testHealthGet() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    String response =
        webClient.get().uri("/actuator/health").retrieve().bodyToMono(String.class).block();
    assertNotNull(response, "null health response");
  }

  @Test
  final void testLogsGet() {
    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();
    String response =
        webClient.get().uri("/actuator/loggers").retrieve().bodyToMono(String.class).block();
    assertNotNull(response, "null loggers response");
  }
}
