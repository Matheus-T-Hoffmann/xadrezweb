package io.github.matheusthoffmann.xadrezweb.repository;

import io.github.matheusthoffmann.xadrezweb.domain.match.Match;
import java.util.List;

import io.github.matheusthoffmann.xadrezweb.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByPlayerWhiteOrPlayerBlack(User white, User black);

}
