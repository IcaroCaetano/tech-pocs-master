package org.example.dungeon.service;

import org.example.dungeon.solver.Dp1dSolver;
import org.example.dungeon.solver.Dp2dSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Service class that decides which dungeon solving algorithm to use based 
 * on a given variant and delegates the computation.
 * 
 * <p>This class supports two solver strategies:
 * <ul>
 *   <li>{@link Dp1dSolver} → solves the dungeon using a 1D dynamic programming approach. Typically used for variant "A".</li>
 *   <li>{@link Dp2dSolver} → solves the dungeon using a 2D dynamic programming approach. Used for variants "B" and "C".</li>
 * </ul>
 *
 * <p>The class also provides a helper method {@code flatten} to convert a 2D dungeon 
 * grid into a 1D array, which is needed for the 1D solver.
 * 
 * <p>Example usage:
 * <pre>
 * int[][] dungeon = { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } };
 * DungeonSolver solver = ...; // Spring will inject dependencies
 * int minHealth = solver.solve(dungeon, "A"); // Uses 1D solver
 * int minHealthB = solver.solve(dungeon, "B"); // Uses 2D solver
 * </pre>
 *
 * <p>This class is annotated with {@code @Component} for Spring dependency injection.
 * It automatically receives instances of {@link Dp1dSolver} and {@link Dp2dSolver}.
 * 
 * @author
 */
@Component
public class DungeonSolver {

    private final Dp1dSolver dp1dSolver;
    private final Dp2dSolver dp2dSolver;

    @Autowired
    public DungeonSolver(Dp1dSolver dp1dSolver, Dp2dSolver dp2dSolver) {
        this.dp1dSolver = dp1dSolver;
        this.dp2dSolver = dp2dSolver;
    }

    /**
     * Solves the dungeon problem using the algorithm determined by the variant.
     *
     * @param dungeon the 2D dungeon grid
     * @param variant the experiment variant ("A", "B", "C", etc.)
     * @return the minimum initial health required to survive the dungeon
     */
    public int solve(int[][] dungeon, String variant) {
        return switch (variant) {
            case "A" -> dp1dSolver.solve(flatten(dungeon));
            case "B", "C" -> dp2dSolver.solve(dungeon);
            default -> dp2dSolver.solve(dungeon);
        };
    }

    /**
     * Flattens a 2D dungeon grid into a 1D array.
     *
     * @param dungeon the 2D dungeon grid
     * @return a 1D array containing all cells in row-major order
     */
    private int[] flatten(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[] flat = new int[m * n];
        int idx = 0;
        for (int[] row : dungeon)
            for (int cell : row)
                flat[idx++] = cell;
        return flat;
    }
}
