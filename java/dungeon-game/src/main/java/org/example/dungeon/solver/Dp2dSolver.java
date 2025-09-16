package org.example.dungeon.solver;

import org.springframework.stereotype.Component;

/**
 * A dynamic programming solver that calculates the minimum initial health 
 * required to traverse a 2D dungeon grid.
 * 
 * <p>Each cell in the dungeon grid contains an integer value:
 * <ul>
 *   <li>Positive values increase the player's health.</li>
 *   <li>Negative values decrease the player's health.</li>
 * </ul>
 * 
 * <p>The player can only move either **right** or **down** at each step, 
 * and must maintain at least 1 unit of health at all times. 
 * The algorithm computes the minimum health required to survive from the start 
 * to the bottom-right corner of the grid using **bottom-up dynamic programming**.
 *
 * <p>Implementation details:
 * <ul>
 *   <li>Uses a 2D DP array {@code dp} with an extra row and column to simplify 
 *       boundary conditions.</li>
 *   <li>Initializes all cells to {@code Integer.MAX_VALUE} to represent unreachable states.</li>
 *   <li>The base cases are set as {@code dp[m][n-1] = dp[m-1][n] = 1}, which represent 
 *       the minimum health required immediately after the destination cell.</li>
 *   <li>Iterates backward from bottom-right to top-left, computing for each cell:
 *       {@code dp[i][j] = max(1, min(dp[i+1][j], dp[i][j+1]) - dungeon[i][j])}.</li>
 * </ul>
 *
 * <p>Example:
 * <pre>
 * dungeon = [
 *   [-2, -3, 3],
 *   [-5, -10, 1],
 *   [10, 30, -5]
 * ]
 * Result = 7
 * </pre>
 * The player must start with at least 7 health to survive the dungeon.
 * 
 * <p>This class is annotated with {@code @Component} for Spring dependency injection.
 * 
 * @author IcaroCaetano
 */
@Component
public class Dp2dSolver {

     /**
     * Solves the dungeon problem for a 2D dungeon grid.
     *
     * @param dungeon the 2D array representing health changes in each room
     * @return the minimum initial health required to survive the dungeon
     */
    public int solve(int[][] dungeon) {
        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
            for (int j = 0; j <= n; j++)
                dp[i][j] = Integer.MAX_VALUE;

        dp[m][n - 1] = dp[m - 1][n] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
                dp[i][j] = Math.max(1, need);
            }
        }
        return dp[0][0];
    }
}
