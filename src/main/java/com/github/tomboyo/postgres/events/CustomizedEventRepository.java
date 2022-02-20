package com.github.tomboyo.postgres.events;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;
import java.util.Set;

public interface CustomizedEventRepository {
  Set<Event> getAllByTags(String... tags) throws JsonProcessingException;
}
