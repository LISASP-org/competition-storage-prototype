package org.lisasp.competitionstorage.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CompetitionDto extends DtoBase {
    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private String shortName;

    @Getter
    @Setter
    @NotNull
    private LocalDate startDate;

    @Getter
    @Setter
    @NotNull
    private LocalDate endDate;

    @Getter
    @Setter
    @NotNull
    private String location;

    @Getter
    @Setter
    @NotNull
    private String country;

    @Getter
    @Setter
    @NotNull
    private String organization;

    @Getter
    @Setter
    private String description;
}
