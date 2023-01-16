package com.iunetworks.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConfig {
  @Bean
  public DirectExchange exchange(){
    return new DirectExchange(" #{spring.rabbitmq.template.exchange}");
  }
  @Bean
  public MessageConverter messageConverter()
  {
    ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    return new Jackson2JsonMessageConverter(mapper);
  }
  @Bean
  public AmqpTemplate template(ConnectionFactory connectionFactory){
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}

