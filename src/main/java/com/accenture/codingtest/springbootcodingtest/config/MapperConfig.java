package com.accenture.codingtest.springbootcodingtest.config;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper constructModelMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        final org.modelmapper.config.Configuration configuration = modelMapper.getConfiguration();
        configuration.setSkipNullEnabled(true);
        configuration.setPropertyCondition(Conditions.isNotNull());
        return modelMapper;
    }
}
