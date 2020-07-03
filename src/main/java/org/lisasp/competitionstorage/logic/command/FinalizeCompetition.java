package org.lisasp.competitionstorage.logic.command;

import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;

public class FinalizeCompetition extends DtoBase implements CompetitionCommand {
    public FinalizeCompetition(String id) {
        super(id);
    }
}
