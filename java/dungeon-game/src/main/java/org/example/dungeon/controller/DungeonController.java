package org.example.dungeon.controller;

import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.service.DungeonService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes dungeon-solving endpoints.
 *
 * <p>This controller handles HTTP requests for solving dungeons. It delegates
 * the computation and experiment variant assignment to {@link DungeonService}.
 *
 * <p>Endpoint:
 * <ul>
 *   <li>POST /api/dungeon/solve → accepts a {@link SolveRequest} and returns a {@link SolveResponse}</li>
 * </ul>
 *
 * <p>Example JSON request body:
 * <pre>
 * {
 *   "playerId": "player123",
 *   "dungeon": [
 *       [-2, -3, 3],
 *       [-5, -10, 1],
 *       [10, 30, -5]
 *   ]
 * }
 * </pre>
 *
 * <p>Example JSON response:
 * <pre>
 * {
 *   "playerId": "player123",
 *   "minHealth": 7,
 *   "variant": "A"
 * }
 * </pre>
 */
@RestController
@RequestMapping("/api/dungeon")
public class DungeonController {

    private final DungeonService service;

    public DungeonController(DungeonService service) {
        this.service = service;
    }

    /**
     * Solves a dungeon for the given player request.
     *
     * @param request the dungeon solve request containing playerId and dungeon grid
     * @return a {@link SolveResponse} with the minimum required health and experiment variant
     */
    @PostMapping("/solve")
    public SolveResponse solve(@RequestBody SolveRequest request) {
        return service.solve(request);
    }
}
