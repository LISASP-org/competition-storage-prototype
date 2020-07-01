package org.lisasp.competitionstorage.logic;

import org.lisasp.competitionstorage.logic.exception.CompetitionNotFoundException;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;
import org.lisasp.competitionstorage.model.Competition;
import org.lisasp.competitionstorage.repository.CompetitionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Competitions {

    private final CompetitionRepository repository;
    private final IdGenerator ids;

    public Competitions(CompetitionRepository repository, IdGenerator idGenerator) {
        this.repository = repository;
        this.ids = idGenerator;
    }

    public List<Competition> find(Optional<String> name) {
        if (name.isPresent()) {
            Competition c = repository.findByName(name.get());
            if (c == null) {
                return new ArrayList<Competition>();
            }
            return Arrays.asList(c);
        }

        return repository.findAll();
    }

    public Competition create(Competition newCompetition) {
        newCompetition.setId(ids.generate());
        return repository.save(newCompetition);
    }

    public void accept(String id) {
        if (id == null || id.isEmpty()) {
            throw new IdMissingException();
        }
        Optional<Competition> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new CompetitionNotFoundException(id);
        }
    }

    public Competition get(String id) {
        if (id == null || id.isEmpty()) {
            throw new IdMissingException();
        }
        Optional<Competition> result = repository.findById(id);
        if (result.isEmpty()) {
            throw new CompetitionNotFoundException(id);
        }
        return result.get();
    }

    public void update(Competition competition) {
        checkIfCompetitionExists(competition.getId());
        create(competition);
    }

    private void checkIfCompetitionExists(String id) {
        get(id);
    }

    public void delete(String id) {
        checkIfCompetitionExists(id);
        repository.deleteById(id);
    }

}
