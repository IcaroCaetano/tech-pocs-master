package org.example.dungeon.dao;

import org.example.dungeon.model.ExposureEntity;
import org.example.dungeon.repository.ExposureRepository;
import org.springframework.stereotype.Component;

@Component
public class ExposureDao {

    private final ExposureRepository repository;

    public ExposureDao(ExposureRepository repository) {
        this.repository = repository;
    }

    public ExposureEntity save(ExposureEntity entity) {
        return repository.save(entity);
    }
}
