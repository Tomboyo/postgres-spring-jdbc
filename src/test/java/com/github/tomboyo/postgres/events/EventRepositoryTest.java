package com.github.tomboyo.postgres.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomboyo.postgres.ObjectMapperConfig;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJdbcTest
@Import(ObjectMapperConfig.class)
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles("integration-test")
@Tag("IT")
class EventRepositoryTest {

  @Autowired
  private EventRepository eventRepository;

  @Test
  public void getAllByTags() throws JsonProcessingException {
    eventRepository.save(Event.createTransient("{}"));
    eventRepository.save(Event.createTransient("{\"tags\": []}"));
    var event1 = eventRepository.save(Event.createTransient("{\"tags\": [\"a\", \"b\"]}"));
    var event2 = eventRepository.save(Event.createTransient("{\"tags\": [\"a\", \"c\"]}"));

    assertEquals(Set.of(event1, event2), eventRepository.getAllByTags("a"));
    assertEquals(Set.of(event2), eventRepository.getAllByTags("c"));
    assertEquals(Set.of(), eventRepository.getAllByTags("none-have-this-tag"));
  }
}