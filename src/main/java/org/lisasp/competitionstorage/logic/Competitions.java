package org.lisasp.competitionstorage.logic;

import org.lisasp.competitionstorage.logic.command.AcceptCompetition;
import org.lisasp.competitionstorage.logic.command.EditCompetition;
import org.lisasp.competitionstorage.logic.command.RegisterCompetition;
import org.lisasp.competitionstorage.logic.exception.CompetitionNotFoundException;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;
import org.lisasp.competitionstorage.model.Competition;
import org.lisasp.competitionstorage.repository.CompetitionRepository;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Competitions {

    private final CompetitionRepository repository;
    private final IdGenerator ids;

    public CompetitionDto apply(RegisterCompetition command) {
        Competition competition = new Competition();
        CompetitionDto result = competition.apply(command);
        repository.save(competition);
        return result;
    }

    public void apply(EditCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        repository.save(competition);
    }

    public void apply(AcceptCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        repository.save(competition);
    }

    public Competitions(CompetitionRepository repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.ids = idGenerator;
    }

    public List<CompetitionDto> find(Optional<String> name) {
        if (name.isPresent()) {
            Competition c = repository.findByName(name.get());
            if (c == null) {
                return new ArrayList<CompetitionDto>();
            }
            return Arrays.asList(c.extractDto());
        }

        return repository.findAll().stream().map(c -> c.extractDto()).collect(Collectors.toList());
    }

    private Competition get(String id) {
        if (id == null || id.isEmpty()) {
            throw new IdMissingException();
        }
        Optional<Competition> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new CompetitionNotFoundException(id);
        }
        return result.get();
    }

    public CompetitionDto find(String id) {
        return get(id).extractDto();
    }

    private void assertExists(String id) {
        get(id);
    }

    public void delete(String id) {
        assertExists(id);
        repository.deleteById(id);
    }
}
