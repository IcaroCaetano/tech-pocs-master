package org.example.dungeon.dao;

import org.example.dungeon.model.DungeonSolutionEntity;
import org.example.dungeon.repository.DungeonSolutionRepository;
import org.springframework.stereotype.Component;

@Component
public class DungeonSolutionDao {

    private final DungeonSolutionRepository repository;

    public DungeonSolutionDao(DungeonSolutionRepository repository) {
        this.repository = repository;
    }

    public DungeonSolutionEntity save(DungeonSolutionEntity entity) {
        return repository.save(entity);
    }
}
