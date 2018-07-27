package com.github.shihyuho.jackson.databind.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.shihyuho.jackson.databind.DynamicFilterMixIn;
import com.github.shihyuho.jackson.databind.DynamicFilterProvider;
import com.github.shihyuho.jackson.databind.DynamicFilterResponseBodyAdvice;
import com.github.shihyuho.jackson.databind.resolver.DynamicFilterResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Map;

@EnableConfigurationProperties(JacksonDynamicFilterProperties.class)
@Configuration
@RequiredArgsConstructor
@Slf4j
public class JacksonDynamicFilterAutoConfiguration {

  private final ListableBeanFactory factory;
  private final JacksonDynamicFilterProperties properties;

  @Bean
  @ConditionalOnMissingBean
  public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.addMixIn(Object.class, DynamicFilterMixIn.class);
    mapper.setFilterProvider(new DynamicFilterProvider());
    return new MappingJackson2HttpMessageConverter(mapper);
  }

  @Bean
  @ConditionalOnMissingBean
  public DynamicFilterResponseBodyAdvice dynamicFilterResponseBodyAdvice()
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    DynamicFilterResponseBodyAdvice advice = new DynamicFilterResponseBodyAdvice();
    String[] classNames = properties.getResolver().getClassNames();
    if (classNames != null) {
      for (String name : classNames) {
        try {
          Class<?> clz = Class.forName(name);
          advice.addResolvers((DynamicFilterResolver) clz.newInstance());
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
          log.error("Failed creating a new instance of the resolver class: {}", name, e);
          if (properties.isFailFast()) {
            throw e;
          }
        }
      }
    }
    Map<String, DynamicFilterResolver> resolvers =
        factory.getBeansOfType(DynamicFilterResolver.class);
    if (resolvers != null) {
      resolvers.values().forEach(advice::addResolvers);
    }
    return advice;
  }
}
