package edu.cnm.deepdive.farkle.configuration;

import java.util.random.RandomGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {
  @Bean
  public RandomGenerator provideRng() {
    return RandomGenerator.getDefault();
  }

}
