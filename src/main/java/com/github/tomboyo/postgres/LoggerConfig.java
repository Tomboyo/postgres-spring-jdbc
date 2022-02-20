package com.github.tomboyo.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {

  @Bean
  public Logger injectLogger(InjectionPoint point) {
    return LoggerFactory.getLogger(point.getDeclaredType());
  }
}
