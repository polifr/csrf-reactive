package it.poli.csrf.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"insecure"})
class CsrfReactiveControllerInsecureTest {

  @Test
  void test() {
    fail("Not yet implemented");
  }
}
