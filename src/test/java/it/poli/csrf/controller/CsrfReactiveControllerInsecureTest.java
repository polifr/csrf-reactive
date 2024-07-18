package it.poli.csrf.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.configuration.NoCsrfSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = CsrfReactiveController.class)
@ActiveProfiles(profiles = {"insecure"})
@Import({NoCsrfSecurityConfiguration.class})
class CsrfReactiveControllerInsecureTest {

  @Autowired(required = false)
  private WebTestClient webClient;

  @MockBean ReactiveJwtDecoder jwtDecoder;

  @Test
  final void testInjection() {
    assertNotNull(webClient, "null webClient");
  }

  @Test
  void testUnauthorizedGet() {
    webClient.get().uri("/csrf/test-get").exchange().expectStatus().isUnauthorized();
  }

  @Test
  void testUnauthorizedPost() {
    webClient.post().uri("/csrf/test-post").exchange().expectStatus().isUnauthorized();
  }

  @Test
  @WithMockUser
  void testGetOk() {
    webClient.get().uri("/csrf/test-get").exchange().expectStatus().isOk();
  }

  @Test
  @WithMockUser
  void testPostOk() {
    webClient.post().uri("/csrf/test-post").exchange().expectStatus().isOk();
  }
}
