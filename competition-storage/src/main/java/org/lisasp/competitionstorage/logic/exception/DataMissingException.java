package org.lisasp.competitionstorage.logic.exception;

public class DataMissingException extends RuntimeException {

    public DataMissingException(String competitionId, String filename) {
        super(String.format("Upload for attachment \"%s\" in competition \"%s\" did not contain data.", filename, competitionId));
    }
}
