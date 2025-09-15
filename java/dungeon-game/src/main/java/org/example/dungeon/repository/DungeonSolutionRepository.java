package org.example.dungeon.repository;

import org.example.dungeon.model.DungeonSolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DungeonSolutionRepository extends JpaRepository<DungeonSolutionEntity, Long> {}
