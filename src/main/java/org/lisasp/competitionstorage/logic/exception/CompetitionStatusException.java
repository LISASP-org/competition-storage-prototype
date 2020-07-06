package org.lisasp.competitionstorage.logic.exception;

import org.lisasp.competitionstorage.model.util.CompetitionStatus;

public class CompetitionStatusException extends RuntimeException {
    public CompetitionStatusException(CompetitionStatus expected, CompetitionStatus actual) {
        super(String.format("Competition should be in status \"%s\" but was in \"%s\".", expected, actual));
    }
}
