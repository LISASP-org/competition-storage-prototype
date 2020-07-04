package org.lisasp.competitionstorage.logic.command;

import lombok.Getter;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;
import org.lisasp.competitionstorage.logic.exception.DtoNotSpecifiedException;

public class UpdateCompetitionProperties implements CompetitionCommand {

    @Getter
    private final CompetitionDto data;

    public UpdateCompetitionProperties(CompetitionDto data) {
        this.data = data;
        validate();
    }

    private void validate() {
        if (data == null) {
            throw new DtoNotSpecifiedException();
        }
    }

    public String getId() {
        return data.getId();
    }
}