package com.iunetworks.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

  @Bean
  @Qualifier("webClientBean")
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder();
  }

  @Bean
  @Primary
  @LoadBalanced
  public WebClient.Builder webClientBuilderLoadBalanced() {
    return WebClient.builder();
  }

}
