package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Competition {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @NotNull
    private String name;

    @Getter
    @Setter
    @NotNull
    private String shortname;

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

    @Getter
    @Setter
    private String comment;

    public Competition() {
    }
}
