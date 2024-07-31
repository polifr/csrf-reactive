package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.configuration.NoCsrfSecurityConfiguration;
import it.poli.csrf.model.loggers.LoggerLevelsDescriptorModel;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"insecure"})
@Import({NoCsrfSecurityConfiguration.class})
class CsrfReactiveApplicationInsecureTests extends CsrfReactiveApplicationCommonTests {

  @ParameterizedTest
  @ValueSource(strings = {"TRACE", "DEBUG", "INFO", "WARN", "ERROR", "OFF"})
  final void testSetLoggerLevel(String level) {
    this.setLoggerLevel("it.poli", level).expectStatus().is2xxSuccessful();
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = this.getLoggerLevelsDescriptor("it.poli");
    assertNotNull(loggerLevelsDescriptor, "null loggerLevelsDescriptor");
    assertEquals(level, loggerLevelsDescriptor.getConfiguredLevel(), "incongruent logger lever");
  }
}
