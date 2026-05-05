package io.github.matheusthoffmann.xadrezweb.controller;

import io.github.matheusthoffmann.xadrezweb.dto.match.MatchResponse;
import io.github.matheusthoffmann.xadrezweb.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MatchResponse> create(
            @RequestParam Long playerWhiteId,
            @RequestParam Long playerBlackId) {

        return ResponseEntity.ok(
                service.create(playerWhiteId, playerBlackId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}