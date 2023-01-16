package com.iunetworks.securityconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

    @Bean
    public ModelMapper getModelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration()
        .setCollectionsMergeEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
      return modelMapper;
    }
  }

