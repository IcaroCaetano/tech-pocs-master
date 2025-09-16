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


/**
 * Service class responsible for handling dungeon-solving requests,
 * assigning experiment variants, recording execution metrics, and 
 * persisting results and exposures in the database.
 *
 * <p>This service performs the following tasks for each SolveRequest:
 * <ol>
 *   <li>Determines the experiment variant for the player using {@link ExperimentAssigner}.</li>
 *   <li>Invokes the appropriate dungeon solver via {@link DungeonSolver}.</li>
 *   <li>Measures the execution time of the solver.</li>
 *   <li>Records metrics in the {@link MeterRegistry} for Prometheus/Grafana.</li>
 *   <li>Stores the player's exposure and the computed solution in the database using {@link ExposureDao} and {@link DungeonSolutionDao}.</li>
 * </ol>
 *
 * <p>The metrics include execution time percentiles (p50, p95, p99) and histograms
 * to analyze solver performance by variant.
 * 
 * <p>Example usage:
 * <pre>
 * SolveRequest request = new SolveRequest(playerId, dungeonGrid);
 * SolveResponse response = dungeonService.solve(request);
 * System.out.println(response.getMinHealth());
 * </pre>
 *
 * <p>This class is annotated with {@code @Service} for Spring dependency injection.
 * It automatically receives the experiment assigner, solver, DAOs, and meter registry.
 * 
 * @author
 */
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

    /**
     * Solves the dungeon for a given player request and records metrics and exposure.
     *
     * @param request the dungeon solve request containing playerId and dungeon grid
     * @return a SolveResponse containing playerId, minimum required health, and variant
     */
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

     /**
     * Records solver execution time in Micrometer for monitoring and analysis.
     *
     * @param variant the experiment variant used
     * @param executionTimeNs the execution time in nanoseconds
     */
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
