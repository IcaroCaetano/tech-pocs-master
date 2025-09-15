package org.example.dungeon.experiment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Configuration
@EnableConfigurationProperties(ExperimentProperties.class)
public class ExperimentConfig {

    @Bean
    public ExperimentAssigner experimentAssigner(ExperimentProperties properties) {
        return new ExperimentAssigner(properties.getSplit());
    }
}
