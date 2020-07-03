package org.lisasp.competitionstorage.logic.command;

import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;

public class RevokeCompetition extends DtoBase implements CompetitionCommand {
    public RevokeCompetition(String id) {
        super(id);
    }
}
