package it.poli.csrf.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.logging.LoggersEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
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
    log.debug("Spring actuator endpoints - no filtering");
    http.authorizeExchange(
        exchange ->
            exchange
                .matchers(
                    EndpointRequest.to(
                        HealthEndpoint.class,
                        InfoEndpoint.class,
                        LoggersEndpoint.class,
                        MetricsEndpoint.class,
                        PrometheusScrapeEndpoint.class))
                .permitAll());

    log.debug("Csrf is not enabled");
    http.csrf(CsrfSpec::disable);

    return http.build();
  }
}
