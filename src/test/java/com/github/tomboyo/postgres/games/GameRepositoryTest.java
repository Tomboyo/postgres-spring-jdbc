package com.github.tomboyo.postgres.games;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@DataJdbcTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("integration-test")
@Tag("IT")
public class GameRepositoryTest {

  @Autowired GameRepository repository;

  @Test
  public void totalPoints() {
    repository.saveAll(
        List.of(
            Game.createTransient(List.of("teamA", "teamB"), List.of(5, 1)),
            Game.createTransient(List.of("teamA", "teamC"), List.of(4, 3))));

    assertEquals(Optional.of(9), repository.totalPoints("teamA"));
    assertEquals(Optional.empty(), repository.totalPoints("no-such-team"));
  }

  @Test
  public void totalWins() {
    repository.saveAll(
        List.of(
            Game.createTransient(List.of("teamA, teamB"), List.of(5, 1)),
            Game.createTransient(List.of("teamA, teamB"), List.of(2, 2)),
            Game.createTransient(List.of("teamA", "teamB"), List.of(1, 5))));

    assertEquals(1, repository.totalWins("teamA"));
    assertEquals(1, repository.totalWins("teamB"));
  }
}
