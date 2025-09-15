package org.example.dungeon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "dungeon_solutions")
public class DungeonSolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String playerId;
    private int minHealthRequired;
    private String variant;

    public DungeonSolutionEntity() {}

    public DungeonSolutionEntity(String playerId, int minHealthRequired, String variant) {
        this.playerId = playerId;
        this.minHealthRequired = minHealthRequired;
        this.variant = variant;
    }

    public Long getId() { return id; }
    public String getPlayerId() { return playerId; }
    public int getMinHealthRequired() { return minHealthRequired; }
    public String getVariant() { return variant; }

    public void setPlayerId(String playerId) { this.playerId = playerId; }
    public void setMinHealthRequired(int minHealthRequired) { this.minHealthRequired = minHealthRequired; }
    public void setVariant(String variant) { this.variant = variant; }
}
