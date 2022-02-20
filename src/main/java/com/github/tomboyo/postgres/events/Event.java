package com.github.tomboyo.postgres.events;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.SQLException;

@Table("events")
public record Event(@Id Integer id, String doc) {
  public static Event createTransient(String doc) {
    return new Event(null, doc);
  }
}
