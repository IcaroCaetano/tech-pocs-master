package org.example.dungeon.experiment;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "experiment")
public class ExperimentProperties {

    private final Map<String, Double> split;

    public ExperimentProperties(Map<String, Double> split) {
        this.split = split;
    }

    public Map<String, Double> getSplit() {
        return split;
    }
}
