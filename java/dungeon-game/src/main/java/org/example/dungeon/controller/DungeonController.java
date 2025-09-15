package org.example.dungeon.controller;

import org.example.dungeon.dto.SolveRequest;
import org.example.dungeon.dto.SolveResponse;
import org.example.dungeon.service.DungeonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dungeon")
public class DungeonController {

    private final DungeonService service;

    public DungeonController(DungeonService service) {
        this.service = service;
    }

    @PostMapping("/solve")
    public SolveResponse solve(@RequestBody SolveRequest request) {
        return service.solve(request);
    }
}
