package it.poli.csrf.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

@Profile("secure")
@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@Slf4j
public class CsrfSecurityConfiguration {

  @Bean
  SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    log.debug("Spring actuator endpoints - no filtering");
    http.authorizeExchange(
        authorize ->
            authorize
                .matchers(EndpointRequest.to(LoggersEndpoint.class, MetricsEndpoint.class))
                .permitAll());

    log.debug("Csrf is enabled");
    http.csrf(
        csrf -> csrf.csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse()));

    return http.build();
  }
}
