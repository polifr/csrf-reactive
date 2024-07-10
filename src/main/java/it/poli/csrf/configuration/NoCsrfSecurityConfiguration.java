package it.poli.csrf.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Profile("insecure")
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@Slf4j
public class NoCsrfSecurityConfiguration {

  @Bean
  SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    log.debug("Csrf is not enabled");
    http.csrf(CsrfSpec::disable);

    return http.build();
  }
}
