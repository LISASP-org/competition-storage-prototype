package org.lisasp.competitionstorage.logic.command;

import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;

public class AcceptCompetition extends DtoBase implements CompetitionCommand {
    public AcceptCompetition(String id) {
        super(id);
    }
}
