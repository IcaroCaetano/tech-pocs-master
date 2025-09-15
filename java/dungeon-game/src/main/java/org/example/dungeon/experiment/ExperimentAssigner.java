package org.example.dungeon.experiment;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ExperimentAssigner {
    private final LinkedHashMap<String, Double> cumulative = new LinkedHashMap<>();

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

    public String choose(String experimentKey, String unitId) {
        var u = uniform01(experimentKey + "|" + unitId);
        return cumulative.entrySet().stream()
                .filter(e -> u <= e.getValue())
                .findFirst()
                .orElseThrow()
                .getKey();
    }

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
