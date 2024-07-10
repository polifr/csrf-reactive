package it.poli.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"insecure"})
class CsrfReactiveApplicationInsecureTests {

  @Test
  void contextLoads() {}
}
