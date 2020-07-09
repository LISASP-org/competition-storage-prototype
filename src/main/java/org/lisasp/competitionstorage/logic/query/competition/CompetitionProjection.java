package org.lisasp.competitionstorage.logic.query.competition;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.lisasp.competitionstorage.logic.api.*;
import org.lisasp.competitionstorage.logic.exception.CompetitionNotFoundException;
import org.lisasp.competitionstorage.logic.model.CompetitionStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CompetitionProjection {

    private final CompetitionRepository repository;

    @EventHandler
    public void handle(CompetitionRegistered event, @Timestamp Instant timestamp) {
        repository.save(new CompetitionEntity(event.getId(), event.getShortName()));
    }

    @EventHandler
    public void handle(CompetitionPropertiesUpdated event, @Timestamp Instant timestamp) {
        Optional<CompetitionEntity> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionEntity entity = queryResult.get();
            entity.setName(event.getName());
            entity.setCountry(event.getCountry());
            entity.setStartDate(event.getStartDate());
            entity.setEndDate(event.getEndDate());
            entity.setLocation(event.getLocation());
            entity.setOrganization(event.getOrganization());
            entity.setDescription(event.getDescription());
            repository.save(entity);
        }
    }

    @EventHandler
    public void handle(CompetitionClosed event, @Timestamp Instant timestamp) {
        Optional<CompetitionEntity> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionEntity entity = queryResult.get();
            entity.setStatus(CompetitionStatus.Closed);
            repository.save(entity);
        }
    }

    @EventHandler
    public void handle(CompetitionFinalized event, @Timestamp Instant timestamp) {
        Optional<CompetitionEntity> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionEntity entity = queryResult.get();
            entity.setStatus(CompetitionStatus.Finalized);
            repository.save(entity);
        }
    }

    @EventHandler
    public void handle(CompetitionReopened event, @Timestamp Instant timestamp) {
        Optional<CompetitionEntity> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionEntity entity = queryResult.get();
            entity.setStatus(CompetitionStatus.Open);
            repository.save(entity);
        }
    }

    @EventHandler
    public void handle(CompetitionRevoked event, @Timestamp Instant timestamp) {
        Optional<CompetitionEntity> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionEntity entity = queryResult.get();
            entity.setStatus(CompetitionStatus.Revoked);
            repository.save(entity);
        }
    }

    @QueryHandler
    public List<CompetitionEntity> findAll(AllCompetitionsQuery query) {
        return repository.findByStatusNot(CompetitionStatus.Revoked);
    }

    @QueryHandler
    public CompetitionEntity findById(CompetitionQuery query) {
        return repository.findById(query.getCompetitionId()).orElseThrow(() -> new CompetitionNotFoundException(query.getCompetitionId()));
    }
}
