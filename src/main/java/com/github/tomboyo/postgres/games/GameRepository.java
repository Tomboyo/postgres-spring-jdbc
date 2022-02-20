package com.github.tomboyo.postgres.games;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameRepository extends CrudRepository<Game, Integer>, CustomizedGameRepository {
  @Query("""
    select sum(points) from (
      select points[1] as points from games where teams[1] = :team
      union
      select points[2] as points from games where teams[2] = :team
    ) a;
  """)
  Optional<Integer> totalPoints(String team);

  @Query("select count(*) from games where winner = :team")
  int totalWins(String team);
}
