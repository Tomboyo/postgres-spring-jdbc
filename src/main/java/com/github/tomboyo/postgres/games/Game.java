package com.github.tomboyo.postgres.games;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("games")
public record Game(@Id Integer id, List<String> teams, List<Integer> points) {
  public static Game createTransient(List<String> teams, List<Integer> points) {
    return new Game(null, teams, points);
  }
}
