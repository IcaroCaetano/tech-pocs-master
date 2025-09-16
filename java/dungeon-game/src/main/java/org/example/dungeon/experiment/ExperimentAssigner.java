package org.example.dungeon.experiment;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Determines experiment variant assignments for users in a deterministic way.
 *
 * <p>This class assigns a unit (e.g., a player) to a variant (A, B, C, etc.)
 * based on the experiment split percentages. Assignments are consistent across
 * multiple calls for the same unitId without storing them in a database.
 *
 * <p>Example:
 * <pre>
 * Map<String, Double> split = Map.of("A", 0.5, "B", 0.3, "C", 0.2);
 * ExperimentAssigner assigner = new ExperimentAssigner(split);
 * String variant = assigner.choose("dungeon_game", "player123");
 * </pre>
 *
 * <p>The assignment is deterministic: the same experimentKey + unitId
 * combination will always produce the same variant.
 */
public final class ExperimentAssigner {
    
    /** Stores cumulative probability thresholds for each variant. */
    private final LinkedHashMap<String, Double> cumulative = new LinkedHashMap<>();

     /**
     * Constructs an ExperimentAssigner from a split map.
     *
     * @param split a map of variant → probability (values must sum to 1.0)
     * @throws IllegalArgumentException if probabilities do not sum to 1.0
     */
    public ExperimentAssigner(Map<String, Double> split) {
        var acc = 0.0;
        for (Map.Entry<String, Double> e : split.entrySet()) {
            acc += e.getValue();
            cumulative.put(e.getKey(), acc);
        }
        if (Math.abs(acc - 1.0) > 1e-9) {
            throw new IllegalArgumentException("Variant split must sum to 1.0");
        }
    }

    /**
     * Chooses a variant for a given unit deterministically.
     *
     * @param experimentKey a unique identifier for the experiment
     * @param unitId a unique identifier for the unit (e.g., playerId)
     * @return the variant assigned to the unit
     */
    public String choose(String experimentKey, String unitId) {
        var u = uniform01(experimentKey + "|" + unitId);
        return cumulative.entrySet().stream()
                .filter(e -> u <= e.getValue())
                .findFirst()
                .orElseThrow()
                .getKey();
    }

     /**
     * Converts a string into a deterministic double in the range [0, 1) using SHA-256.
     *
     * @param s the input string (experimentKey + "|" + unitId)
     * @return a deterministic pseudo-random double between 0 (inclusive) and 1 (exclusive)
     */
    private static double uniform01(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            var digest = md.digest(s.getBytes()); // 32 bytes (256 bits)
            // Use first 8 bytes as unsigned long
            var hiBytes = new byte[8];
            System.arraycopy(digest, 0, hiBytes, 0, 8);
            BigInteger hi = new BigInteger(1, hiBytes); // Unsigned
            // Divide by 2^64 to get [0,1)
            return hi.doubleValue() / Math.pow(2, 64);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
