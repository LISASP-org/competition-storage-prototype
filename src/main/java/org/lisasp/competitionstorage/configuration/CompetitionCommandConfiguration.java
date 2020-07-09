package org.lisasp.competitionstorage.configuration;

import org.axonframework.common.caching.Cache;
import org.axonframework.common.caching.WeakReferenceCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("command")
public class CompetitionCommandConfiguration {

    /*
    @Bean
    public Repository<Competition> createCompetitionRepository(EventStore eventStore, Cache cache) {
        return EventSourcingRepository.builder(Competition.class)
                .cache(cache)
                .eventStore(eventStore)
                .build();
    }*/

    @Bean
    public Cache cache() {
        return new WeakReferenceCache();
    }
}
