package com.github.tomboyo.postgres.events;

import org.springframework.data.repository.CrudRepository;

public interface EventRepository extends CrudRepository<Event, Integer>, CustomizedEventRepository {}
