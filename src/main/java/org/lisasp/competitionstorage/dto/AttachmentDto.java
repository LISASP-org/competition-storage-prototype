package org.lisasp.competitionstorage.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class AttachmentDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    private byte[] data;
}
