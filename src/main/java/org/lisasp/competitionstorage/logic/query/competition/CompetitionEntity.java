package org.lisasp.competitionstorage.logic.query.competition;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.logic.competition.CompetitionStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
public class CompetitionEntity {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @NotNull
    private String shortName;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private CompetitionStatus status = CompetitionStatus.Open;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private LocalDate startDate;

    @Getter
    @Setter
    private LocalDate endDate;

    @Getter
    @Setter
    private String location;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String organization;

    @Getter
    @Setter
    private String description;

    public CompetitionEntity() {
    }

    public CompetitionEntity(@NotBlank String id, @NotBlank String shortName) {
        this.id = id;
        this.shortName = shortName;
    }
}
