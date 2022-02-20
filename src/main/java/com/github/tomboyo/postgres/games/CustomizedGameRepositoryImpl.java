package com.github.tomboyo.postgres.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static java.util.Objects.requireNonNull;

public class CustomizedGameRepositoryImpl implements CustomizedGameRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public CustomizedGameRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int totalWins(String team) {
    return requireNonNull(jdbcTemplate.queryForObject(
        "select count(*) from games where winner = ?",
        Integer.class));
  }
}
