package com.github.shihyuho.jackson.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class MyConfiguration {

  @Bean
  public MappingJackson2HttpMessageConverter indentOutputMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return new MappingJackson2HttpMessageConverter(mapper);
  }

  @Bean
  public DynamicFilterResponseBodyAdvice dynamicFilterResponseBodyAdvice() {
    return new DynamicFilterResponseBodyAdvice();
  }
}
