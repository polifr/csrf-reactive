package it.poli.csrf.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(controllers = CsrfReactiveController.class)
@ActiveProfiles(profiles = {"secure"})
class CsrfReactiveControllerSecureTest {

  @Test
  void test() {
    fail("Not yet implemented");
  }
}
