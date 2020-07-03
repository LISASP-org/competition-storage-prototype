package org.lisasp.competitionstorage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class AssetDto extends DtoBase {
    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    private byte[] data;
}
