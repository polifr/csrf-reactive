package it.poli.csrf;

import it.poli.csrf.configuration.CsrfSecurityConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = {"secure"})
@Import({CsrfSecurityConfiguration.class})
class CsrfReactiveApplicationSecureTests extends CsrfReactiveApplicationCommonTests {}
