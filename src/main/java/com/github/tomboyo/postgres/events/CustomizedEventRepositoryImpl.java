package com.github.tomboyo.postgres.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomizedEventRepositoryImpl implements CustomizedEventRepository {

  private final JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper;

  @Autowired
  public CustomizedEventRepositoryImpl(
      JdbcTemplate jdbcTemplate,
      ObjectMapper objectMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.objectMapper = objectMapper;
  }

  @Override
  public Set<Event> getAllByTags(String... tags) throws JsonProcessingException {
    // Json sanitization to prevent injection, since postgresql does not support query parameters within json/b literal
    // strings.
    var tagsFragment = objectMapper.writeValueAsString(tags);
    var query = "{\"tags\": " + tagsFragment + "}";

    return new HashSet<>(jdbcTemplate.query(
        "select * from events where doc @> ?",
        rm(),
        query));
  }

  private static RowMapper<Event> rm() {
    return (rs, num) -> new Event(rs.getInt("id"), rs.getString("doc"));
  }
}
