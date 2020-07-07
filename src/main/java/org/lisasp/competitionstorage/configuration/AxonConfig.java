package org.lisasp.competitionstorage.configuration;

import org.axonframework.config.Configurer;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.lisasp.competitionstorage.model.Competition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {

    @Bean
    EventSourcingRepository<Competition> accountAggregateEventSourcingRepository(EventStore eventStore){
        return EventSourcingRepository.builder(Competition.class).eventStore(eventStore).build();
    }

    @Autowired
    public void serializerConfiguration(Configurer configurer) {
        Serializer defaultSerializer = JacksonSerializer.defaultSerializer();
        configurer.configureSerializer(configuration -> defaultSerializer)
                .configureMessageSerializer(configuration -> defaultSerializer)
                .configureEventSerializer(configuration -> defaultSerializer);
    }
}
