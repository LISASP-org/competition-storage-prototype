package org.lisasp.competitionstorage.logic.command;

import lombok.Getter;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;

public class UpdateCompetition implements CompetitionCommand {

    @Getter
    private final CompetitionDto data;

    public UpdateCompetition(CompetitionDto data) {
        this.data = data;
        validate();
    }

    private void validate() {
        if (data == null) {
            throw new NullPointerException();
        }
    }

    public String getId() {
        return data.getId();
    }
}
