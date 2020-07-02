package org.lisasp.competitionstorage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public abstract class DtoBase {

    @Getter
    @Setter
    @NotNull
    private String id;

    public DtoBase() {
    }

    public DtoBase(String id) {
        this.id = id;
    }
}
