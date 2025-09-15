package org.example.dungeon.solver;

import org.springframework.stereotype.Component;

@Component
public class Dp1dSolver {

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
