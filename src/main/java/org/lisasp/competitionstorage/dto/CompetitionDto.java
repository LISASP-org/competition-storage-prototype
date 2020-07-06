package org.lisasp.competitionstorage.dto;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
public class CompetitionDto {

    @NotBlank
    @Min(16)
    private String id;

    @NotBlank
    @Min(8)
    @Max(100)
    private String name;

    @NotNull
    private String shortName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private String location;

    @NotBlank
    private String country;

    @NotBlank
    private String organization;

    private String description;
}
