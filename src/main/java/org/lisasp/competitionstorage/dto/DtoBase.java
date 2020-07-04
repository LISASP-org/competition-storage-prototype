package org.lisasp.competitionstorage.dto;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;

import javax.validation.constraints.NotNull;

public abstract class DtoBase {

    @Getter
    @Setter
    @NotNull
    private String id;

    public DtoBase() {
    }

    public DtoBase(@NotNull String id) {
        if (id == null) {
            throw new IdMissingException();
        }
        this.id = id;
    }
}
