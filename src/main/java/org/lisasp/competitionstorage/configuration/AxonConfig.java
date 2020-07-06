package org.lisasp.competitionstorage.configuration;

import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.lisasp.competitionstorage.model.Competition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    EventSourcingRepository<Competition> accountAggregateEventSourcingRepository(EventStore eventStore){
        EventSourcingRepository<Competition> repository = EventSourcingRepository.builder(Competition.class).eventStore(eventStore).build();
        return repository;
    }
}
