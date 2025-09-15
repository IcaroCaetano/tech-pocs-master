package org.example.dungeon.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.example.dungeon.dao.DungeonSolutionDao;
import org.example.dungeon.dao.ExposureDao;
import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.experiment.ExperimentAssigner;
import org.example.dungeon.model.DungeonSolutionEntity;
import org.example.dungeon.model.ExposureEntity;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

@Service
public class DungeonService {

    private final ExperimentAssigner experimentAssigner;
    private final DungeonSolver solver;
    private final DungeonSolutionDao solutionDao;
    private final ExposureDao exposureDao;
    private final MeterRegistry meterRegistry;

    public DungeonService(ExperimentAssigner experimentAssigner,
                          DungeonSolver solver,
                          DungeonSolutionDao solutionDao,
                          ExposureDao exposureDao,
                          MeterRegistry meterRegistry) {
        this.experimentAssigner = experimentAssigner;
        this.solver = solver;
        this.solutionDao = solutionDao;
        this.exposureDao = exposureDao;
        this.meterRegistry = meterRegistry;
    }

    public SolveResponse solve(SolveRequest request) {
        var variant = experimentAssigner.choose("dungeon_game", request.playerId());

        var start = System.nanoTime();
        var minHealth = solver.solve(request.dungeon(), variant);
        var executionTimeNs = System.nanoTime() - start;

        saveMetrics(variant, executionTimeNs);
        exposureDao.save(new ExposureEntity(request.playerId(), "dungeon_game", variant, executionTimeNs / 1_000_000.0));
        solutionDao.save(new DungeonSolutionEntity(request.playerId(), minHealth, variant));
        return new SolveResponse(request.playerId(), minHealth, variant);
    }

    private void saveMetrics(String variant, long executionTimeNs) {
        String timerName = variant.equals("A") ? "dp1d.solver.execution" : "dp2d.solver.execution";
        Timer.builder(timerName)
            .description("Time taken to execute " + timerName)
            .publishPercentiles(0.5, 0.95, 0.99)
            .publishPercentileHistogram()
            .register(meterRegistry)
            .record(executionTimeNs, NANOSECONDS);
    }
}
