csrf-reactive

This projects aims to experiment the usages of CSRF protection on the Spring Reactive stack.

July 2024 - testing the interaction between CSRF protection and the usage of Spring Actuator endpoints for modifying the logging levels; in this case, since the reactive approach does not implement the equivalent "ignoringRequestMatchers" method,
it seems that there is no way to enable these endpoints for POST calls other than re-declare the requireCsrfProtectionMatcher.
