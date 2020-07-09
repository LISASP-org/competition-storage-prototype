package org.lisasp.competitionstorage.logic.exception;

import org.lisasp.competitionstorage.logic.model.CompetitionStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CompetitionStatusException extends RuntimeException {
    public CompetitionStatusException(CompetitionStatus[] expected, CompetitionStatus actual) {
        super(String.format("Competition should be in status \"%s\" but was in \"%s\".", toString(expected), actual));
    }

    private static String toString(CompetitionStatus[] expected) {
        if (expected == null || expected.length == 0) {
            return "<empty>";
        }
        return String.join("\", \"", Arrays.asList(expected).stream().map(c -> c.name()).collect(Collectors.toList()));
    }
}
