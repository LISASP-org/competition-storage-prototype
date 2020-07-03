package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.IdGenerator;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.AssetNotFoundException;
import org.lisasp.competitionstorage.logic.exception.CompetitionAlreadyExistsException;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Competition {

    private static final IdGenerator ids = new IdGenerator();

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

    @Getter
    @Setter
    private List<Asset> assets = new ArrayList<>();

    @Getter
    @Setter
    private List<Result> results = new ArrayList<>();

    public Competition() {
    }

    public void apply(RegisterCompetition command) {
        assertNew();
        importData(command.getData());
        initialize();
    }

    public void apply(UpdateCompetition command) {
        importData(command.getData());
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

    public String apply(AddAsset addAsset) {
        Asset asset = new Asset();
        asset.setId(ids.generate());
        asset.setName(addAsset.getName());
        asset.setData(addAsset.getData());
        assets.add(asset);
        return asset.getId();
    }

    public String apply(UpdateAsset updateAsset) {
        Asset asset = getAsset(updateAsset.getAssetId());
        asset.setName(updateAsset.getName());
        asset.setData(updateAsset.getData());
        assets.add(asset);
        return asset.getId();
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
        dto.setShortName(shortName);
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
        shortName = dto.getShortName();
        startDate = dto.getStartDate();
        endDate = dto.getEndDate();
        location = dto.getLocation();
        country = dto.getCountry();
        organization = dto.getOrganization();
        description = dto.getDescription();
    }

    private Asset getAsset(String assetId) {
        Optional<Asset> asset = assets.stream().filter(a -> a.getId().equals(assetId)).findFirst();
        if (asset.isEmpty()) {
            throw new AssetNotFoundException(getId(), assetId);
        }
        return asset.get();
    }

    public Optional<AssetDto> getAssetDto(String assetId) {
        return assets.stream().filter(a -> a.getId().equals(assetId)).map(a -> a.extractDto()).findFirst();
    }

    public List<AssetDto> getAssetDtos() {
        return assets.stream().map(a -> a.extractDto()).collect(Collectors.toList());
    }

    public void apply(RemoveAsset removeAsset) {
        assets.removeIf(a -> a.getId().equals(removeAsset.getAssetId()));
    }
}
