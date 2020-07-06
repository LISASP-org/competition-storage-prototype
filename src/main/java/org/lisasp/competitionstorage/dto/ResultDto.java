package org.lisasp.competitionstorage.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class ResultDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private byte[] data;
}
