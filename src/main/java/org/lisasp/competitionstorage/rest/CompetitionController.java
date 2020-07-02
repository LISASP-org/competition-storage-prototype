package org.lisasp.competitionstorage.rest;

import org.lisasp.competitionstorage.logic.Competitions;
import org.lisasp.competitionstorage.logic.command.EditCompetition;
import org.lisasp.competitionstorage.logic.command.RegisterCompetition;
import org.lisasp.competitionstorage.logic.exception.IdsNotValidException;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.command.AcceptCompetition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CompetitionController {

    private final Competitions competitions;

    @Autowired
    CompetitionController(Competitions competitions) {
        this.competitions = competitions;
    }

    @GetMapping("/competitions")
    public List<CompetitionDto> find(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        return competitions.find(validate(name));
    }

    @GetMapping("/competitions/{id}")
    CompetitionDto findById(@PathVariable String id) {
        return competitions.find(id);
    }

    private Optional<String> validate(String name) {
        if (name != null && !name.trim().isEmpty()) {
            return Optional.of(name.trim());
        }
        return Optional.empty();
    }

    @PostMapping("/competitions")
    CompetitionDto register(@RequestBody CompetitionDto competition) {
        return competitions.apply(new RegisterCompetition(competition));
    }

    @PostMapping("/competitions/{id}/accept")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void accept(@RequestBody AcceptCompetition accept, @PathVariable String id) {
        competitions.apply(new AcceptCompetition(id));
    }

    @PutMapping("/competitions/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void update(@RequestBody CompetitionDto competition, @PathVariable String id) {
        checkValidityOfIds(competition.getId(), id);
        competitions.apply(new EditCompetition(competition));
    }

    @DeleteMapping("/competitions/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void delete(@PathVariable String id) {
        competitions.delete(id);
    }

    private void checkValidityOfIds(String id1, String id2) {
        if (!areValid(id1, id2)) {
            throw new IdsNotValidException(id1, id2);
        }
    }

    private boolean areValid(String id1, String id2) {
        if (isNullOrEmpty(id1)) {
            return false;
        }
        if (isNullOrEmpty(id2)) {
            return false;
        }
        return id1.equals(id2);
    }

    private boolean isNullOrEmpty(String id) {
        if (id == null) {
            return true;
        }
        return id.trim().isEmpty();
    }
}
