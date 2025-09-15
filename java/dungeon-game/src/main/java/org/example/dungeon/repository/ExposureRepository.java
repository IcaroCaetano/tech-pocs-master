package org.example.dungeon.repository;

import org.example.dungeon.model.ExposureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExposureRepository extends JpaRepository<ExposureEntity, Long> {}
