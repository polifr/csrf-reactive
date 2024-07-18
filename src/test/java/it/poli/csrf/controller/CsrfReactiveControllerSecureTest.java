package it.poli.csrf.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(controllers = CsrfReactiveController.class)
@ActiveProfiles(profiles = {"secure"})
@Import({CsrfSecurityConfiguration.class})
class CsrfReactiveControllerSecureTest {

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
    webClient
        .mutateWith(SecurityMockServerConfigurers.csrf())
        .post()
        .uri("/csrf/test-post")
        .exchange()
        .expectStatus()
        .isUnauthorized();
  }

  @Test
  @WithMockUser
  void testGetOk() {
    webClient.get().uri("/csrf/test-get").exchange().expectStatus().isOk();
  }

  @Test
  @WithMockUser
  void testForbiddenPost() {
    webClient.post().uri("/csrf/test-post").exchange().expectStatus().isForbidden();
  }

  @Test
  @WithMockUser
  void testPostOk() {
    webClient
        .mutateWith(SecurityMockServerConfigurers.csrf())
        .post()
        .uri("/csrf/test-post")
        .exchange()
        .expectStatus()
        .isOk();
  }
}
