package org.lisasp.competitionstorage.repository;

import org.lisasp.competitionstorage.model.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetitionRepository extends MongoRepository<Competition, String> {
    Competition findByName(String name);
}
