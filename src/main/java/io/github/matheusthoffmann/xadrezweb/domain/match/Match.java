package io.github.matheusthoffmann.xadrezweb.domain.match;

import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User playerWhite;

    @ManyToOne
    private User playerBlack;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @ManyToOne
    private User winner;

    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;

    public Match() {}

    public Match(User playerWhite, User playerBlack) {
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        this.status = MatchStatus.IN_PROGRESS;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public User getPlayerWhite() { return playerWhite; }
    public User getPlayerBlack() { return playerBlack; }
    public MatchStatus getStatus() { return status; }
    public User getWinner() { return winner; }

    public void finish(User winner) {
        this.status = MatchStatus.FINISHED;
        this.winner = winner;
        this.finishedAt = LocalDateTime.now();
    }
}