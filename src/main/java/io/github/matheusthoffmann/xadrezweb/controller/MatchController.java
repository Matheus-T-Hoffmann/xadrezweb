package io.github.matheusthoffmann.xadrezweb.controller;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import io.github.matheusthoffmann.xadrezweb.dto.board.BoardResponse;
import io.github.matheusthoffmann.xadrezweb.dto.match.MatchResponse;
import io.github.matheusthoffmann.xadrezweb.dto.match.MatchmakingResponse;
import io.github.matheusthoffmann.xadrezweb.dto.match.MoveRequest;
import io.github.matheusthoffmann.xadrezweb.exception.ResourceNotFoundException;
import io.github.matheusthoffmann.xadrezweb.repository.UserRepository;
import io.github.matheusthoffmann.xadrezweb.service.MatchService;
import io.github.matheusthoffmann.xadrezweb.service.MatchmakingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService service;
    private final UserRepository userRepository;
    private final MatchmakingService matchmakingService;

    public MatchController(MatchService service, UserRepository userRepository, MatchmakingService matchmakingService) {
        this.service = service;
        this.userRepository = userRepository;
        this.matchmakingService = matchmakingService;
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

    @GetMapping("/{id}/board")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(service.getBoard(id));
    }

    @PostMapping("/{id}/moves")
    public ResponseEntity<?> makeMove(
            @PathVariable Long id,
            @RequestBody MoveRequest request) {

        service.makeMove(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/find")
    public ResponseEntity<?> findMatch(@RequestParam Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Match match = matchmakingService.findMatch(user);

        if (match == null) {
            return ResponseEntity.ok(new MatchmakingResponse("WAITING", null));
        }

        return ResponseEntity.ok(new MatchmakingResponse("MATCH_FOUND", match.getId()));
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestParam Long userId) {
        matchmakingService.cancel(userId);
        return ResponseEntity.ok().build();
    }
}