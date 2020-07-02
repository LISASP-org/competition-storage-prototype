package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.IdGenerator;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.CompetitionAlreadyExistsException;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Competition {

    private static IdGenerator ids = new IdGenerator();

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
    private List<Asset> assets = new ArrayList<>();

    @Getter
    @Setter
    private List<Result> results = new ArrayList<>();

    public Competition() {
    }

    public CompetitionDto apply(RegisterCompetition command) {
        assertNew();
        importData(command.getData());
        initialize();
        return extractDto();
    }

    public CompetitionDto apply(EditCompetition command) {
        importData(command.getData());
        return extractDto();
    }

    public void apply(AcceptCompetition command) {
        // Todo: Not yet implemented
    }

    public void apply(FinalizeCompetition command) {
        // Todo: Not yet implemented
    }

    public void apply(RevokeCompetition command) {
        // Todo: Not yet implemented
    }

    public void apply(ReopenCompetition command) {
        // Todo: Not yet implemented
    }

    private void assertNew() {
        if (id != null && !id.trim().isEmpty()) {
            throw new CompetitionAlreadyExistsException(id);
        }
    }

    public CompetitionDto extractDto() {
        CompetitionDto dto = new CompetitionDto();
        dto.setId(id);
        dto.setName(name);
        dto.setShortname(shortname);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);
        dto.setLocation(location);
        dto.setCountry(country);
        dto.setOrganization(organization);
        dto.setDescription(description);
        return dto;
    }

    private void initialize() {
        id = ids.generate();
        if (assets == null) {
            assets = new ArrayList<>();
        }
        if (results == null) {
            results = new ArrayList<>();
        }
    }

    private void importData(CompetitionDto dto) {
        id = dto.getId();
        name = dto.getName();
        shortname = dto.getShortname();
        startDate = dto.getStartDate();
        endDate = dto.getEndDate();
        location = dto.getLocation();
        country = dto.getCountry();
        organization = dto.getOrganization();
        description = dto.getDescription();
    }
}
