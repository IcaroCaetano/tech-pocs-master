package org.example.dungeon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "experiment_exposures")
public class ExposureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerId;
    private String experimentKey;
    private String variant;
    private Double executionTimeMs;

    public ExposureEntity() {}
    public ExposureEntity(String playerId, String experimentKey, String variant, Double executionTimeMs) {
        this.playerId = playerId;
        this.experimentKey = experimentKey;
        this.variant = variant;
        this.executionTimeMs = executionTimeMs;
    }

    public Long getId() { return id; }
    public String getPlayerId() { return playerId; }
    public String getExperimentKey() { return experimentKey; }
    public String getVariant() { return variant; }
    public Double getExecutionTimeMs() { return executionTimeMs; }

    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public void setExperimentKey(String experimentKey) { this.experimentKey = experimentKey; }
    public void setVariant(String variant) { this.variant = variant; }
    public void setExecutionTimeMs(Double executionTimeMs) { this.executionTimeMs = executionTimeMs; }
}
