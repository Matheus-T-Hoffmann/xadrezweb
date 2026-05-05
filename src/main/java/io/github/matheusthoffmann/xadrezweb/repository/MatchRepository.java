package io.github.matheusthoffmann.xadrezweb.repository;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
