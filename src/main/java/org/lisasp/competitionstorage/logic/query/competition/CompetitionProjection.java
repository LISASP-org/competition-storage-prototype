package org.lisasp.competitionstorage.logic.query.competition;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.queryhandling.QueryHandler;
import org.lisasp.competitionstorage.logic.event.*;
import org.lisasp.competitionstorage.logic.model.CompetitionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class CompetitionProjection {

    private final CompetitionRepository repository;

    @Autowired
    public CompetitionProjection(CompetitionRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void handle(CompetitionRegistered event, @Timestamp Instant timestamp) {
        repository.save(new CompetitionDto(event.getId(), event.getShortName()));
    }

    @EventHandler
    public void handle(CompetitionPropertiesUpdated event, @Timestamp Instant timestamp) {
        Optional<CompetitionDto> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionDto dto = queryResult.get();
            dto.setName(event.getName());
            dto.setCountry(event.getName());
            dto.setStartDate(event.getStartDate());
            dto.setEndDate(event.getEndDate());
            dto.setLocation(event.getLocation());
            dto.setOrganization(event.getOrganization());
            dto.setDescription(event.getDescription());
        }
    }

    @EventHandler
    public void handle(CompetitionClosed event, @Timestamp Instant timestamp) {
        Optional<CompetitionDto> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionDto dto = queryResult.get();
            dto.setStatus(CompetitionStatus.Closed);
        }
    }

    @EventHandler
    public void handle(CompetitionFinalized event, @Timestamp Instant timestamp) {
        Optional<CompetitionDto> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionDto dto = queryResult.get();
            dto.setStatus(CompetitionStatus.Finalized);
        }
    }

    @EventHandler
    public void handle(CompetitionReopened event, @Timestamp Instant timestamp) {
        Optional<CompetitionDto> queryResult = repository.findById(event.getId());
        if (queryResult.isPresent()) {
            CompetitionDto dto = queryResult.get();
            dto.setStatus(CompetitionStatus.Open);
        }
    }

    @QueryHandler
    public List<CompetitionDto> handle(AllCompetitionsQuery query) {
        return repository.findAll();
    }
}
