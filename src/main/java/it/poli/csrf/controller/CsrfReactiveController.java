package it.poli.csrf.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CsrfReactiveController {

  @GetMapping("/csrf/test-get")
  public Mono<ResponseEntity<Void>> testGet() {
    return Mono.just(ResponseEntity.ok().build());
  }

  @PostMapping("/csrf/test-post")
  public Mono<ResponseEntity<Void>> testPost() {
    return Mono.just(ResponseEntity.ok().build());
  }
}
