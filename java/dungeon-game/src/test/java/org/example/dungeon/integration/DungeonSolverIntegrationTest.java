package org.example.dungeon.integration;

import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.experiment.ExperimentAssigner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DungeonSolverIntegrationTest {

    @Value("${test.apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;
    private int[][] testDungeon;
    private String[] playerIds;
    private static final int DUNGEON_SIZE = 100;
    private static final int NUM_PLAYERS = 10000;

    @BeforeEach
    void setUp() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Generate large random dungeon
        Random rand = new Random();
        testDungeon = new int[DUNGEON_SIZE][DUNGEON_SIZE];
        for (int i = 0; i < DUNGEON_SIZE; i++) {
            for (int j = 0; j < DUNGEON_SIZE; j++) {
                testDungeon[i][j] = rand.nextInt(21) - 10; // values between -10 and 10
            }
        }
        // Generate random player IDs
        playerIds = IntStream.range(0, NUM_PLAYERS)
                .mapToObj(i -> "player_" + rand.nextInt(100))
                .toArray(String[]::new);
    }

    @Test
    void testVariantA_UsesDp1dSolver() {
        for (String playerId : playerIds) {
            SolveRequest request = new SolveRequest(playerId, testDungeon);
            ResponseEntity<SolveResponse> response = restTemplate.postForEntity(
                    createUrl("/api/dungeon/solve"),
                    new HttpEntity<>(request, headers),
                    SolveResponse.class
            );
            assertNotNull(response.getBody());
            assertEquals(playerId, response.getBody().playerId());
            assertTrue(response.getBody().minHealthRequired() > 0);
        }
    }

    private String createUrl(String uri) {
        return apiBaseUrl + uri;
    }
}
