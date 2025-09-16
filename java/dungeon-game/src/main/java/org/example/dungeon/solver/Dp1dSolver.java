package org.example.dungeon.solver;

import org.springframework.stereotype.Component;

/**
 * A dynamic programming solver that calculates the minimum initial health 
 * required to traverse a dungeon represented as a 1D array of rooms.
 * 
 * <p>Each room contains an integer value:
 * <ul>
 *   <li>If the value is positive, it increases the player's health.</li>
 *   <li>If the value is negative, it decreases the player's health.</li>
 * </ul>
 * 
 * <p>The player must maintain at least 1 unit of health at all times.
 * The algorithm works backwards from the last room, ensuring that 
 * the required health before entering each room is enough to survive 
 * through to the end.
 *
 * <p>For example:
 * <pre>
 * rooms = [-2, -3, 3]
 * Result = 6
 * </pre>
 * The player needs at least 6 health points at the start to survive.
 * 
 * <p>Implementation details:
 * <ul>
 *   <li>Uses a 1D dynamic programming array {@code dp} where {@code dp[i]} 
 *       stores the minimum health required before entering room {@code i}.</li>
 *   <li>The base case is {@code dp[n] = 1}, meaning at least 1 health is 
 *       required after the last room.</li>
 *   <li>Iterates backwards to compute the required health at each step.</li>
 * </ul>
 *
 * @author 
 */
@Component
public class Dp1dSolver {

    /**
     * Solves the dungeon problem for a 1D dungeon array.
     *
     * @param rooms the array representing health changes in each room
     * @return the minimum initial health required to survive all rooms
     */
    public int solve(int[] rooms) {
        int n = rooms.length;
        int[] dp = new int[n + 1];
        dp[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = Math.max(1, dp[i + 1] - rooms[i]);
        }
        return dp[0];
    }
}
