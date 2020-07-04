package org.lisasp.competitionstorage.logic;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.CompetitionNotFoundException;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;
import org.lisasp.competitionstorage.model.Competition;
import org.lisasp.competitionstorage.repository.CompetitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class Competitions {

    private final CompetitionRepository repository;

    public String apply(RegisterCompetition command) {
        Competition competition = new Competition();
        competition.apply(command);
        competition.validate();
        repository.save(competition);
        return competition.getId();
    }

    public void apply(UpdateCompetitionProperties command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        competition.validate();
        repository.save(competition);
    }

    public void apply(AcceptCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        competition.validate();
        repository.save(competition);
    }

    public void apply(RevokeCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        competition.validate();
        repository.save(competition);
    }

    public void apply(ReopenCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        competition.validate();
        repository.save(competition);
    }

    public void apply(FinalizeCompetition command) {
        Competition competition = get(command.getId());
        competition.apply(command);
        competition.validate();
        repository.save(competition);
    }

    public String apply(AddAsset addAsset) {
        Competition competition = get(addAsset.getId());
        String assetId = competition.apply(addAsset);
        competition.validate();
        repository.save(competition);
        return assetId;
    }

    public void apply(UpdateAsset addAsset) {
        Competition competition = get(addAsset.getId());
        competition.apply(addAsset);
        competition.validate();
        repository.save(competition);
    }

    @Autowired
    public Competitions(CompetitionRepository repository) {
        this.repository = repository;
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

    public List<CompetitionDto> findByName(String name) {
        Competition c = repository.findByName(toValidName(name));
        if (c == null) {
            return new ArrayList<>();
        }
        return Collections.singletonList(c.extractDto());
    }

    private String toValidName(String name) {
        if (name == null) {
            return "";
        }
        return name.trim();
    }

    public List<CompetitionDto> findAll() {
        return repository.findAll().stream().map(c -> c.extractDto()).collect(Collectors.toList());
    }

    public AssetDto findAsset(String competitionId, String assetId) {
        Competition c = get(competitionId);
        return c.getAssetDto(assetId);
    }

    public List<AssetDto> findAssets(String id) {
        Competition c = get(id);
        return c.getAssetDtos();
    }

    public void removeAsset(RemoveAsset removeAsset) {
        Competition c = get(removeAsset.getId());
        c.apply(removeAsset);
        repository.save(c);
    }
}
