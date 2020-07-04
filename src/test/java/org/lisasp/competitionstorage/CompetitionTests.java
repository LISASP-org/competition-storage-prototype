package org.lisasp.competitionstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.model.Competition;

import java.time.LocalDate;
import java.util.UUID;

class CompetitionTests {

    private static CompetitionDto createDto(String name) {
        CompetitionDto dto = new CompetitionDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setName(name);
        dto.setCountry("Country");
        dto.setDescription("Description");
        dto.setEndDate(LocalDate.of(2020, 7, 4));
        dto.setLocation("Location");
        dto.setOrganization("Organization");
        dto.setShortName("Short name");
        dto.setStartDate(LocalDate.of(2020, 7, 3));
        return dto;
    }

    private static AssetDto createAssetDto() {
        AssetDto dto = new AssetDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setName("protocol.pdf");
        dto.setData(new byte[]{0x0, 0x1, 0x2, 0x3});
        return dto;
    }

    @BeforeEach
    void prepare() {
        dto1 = createDto("dto1");
        competition = new Competition();

        competition.apply(new RegisterCompetition(dto1));

        dto2 = createDto("dto2");
        dto2.setId(competition.getId());

        assetDto = createAssetDto();
    }

    private CompetitionDto dto1;
    private CompetitionDto dto2;
    private AssetDto assetDto;
    private Competition competition;

    @Test
    void registerCompetition() {
        Assertions.assertNotNull(competition.getId(), "Id must not be empty");
        Assertions.assertFalse(competition.getId().trim().isEmpty(), "Id must not be empty");
        Assertions.assertNotEquals(competition.getId(), dto1.getId(), "Id should not be taken from dto.");
        Assertions.assertEquals(competition.getName(), dto1.getName(), "Name property must be taken from dto.");
        Assertions.assertEquals(competition.getCountry(), dto1.getCountry(), "Country property must be taken from dto.");
        Assertions.assertEquals(competition.getDescription(), dto1.getDescription(), "Description property must be taken from dto.");
        Assertions.assertEquals(competition.getEndDate(), dto1.getEndDate(), "EndDate property must be taken from dto.");
        Assertions.assertEquals(competition.getLocation(), dto1.getLocation(), "Location property must be taken from dto.");
        Assertions.assertEquals(competition.getOrganization(), dto1.getOrganization(), "Organization property must be taken from dto.");
        Assertions.assertEquals(competition.getShortName(), dto1.getShortName(), "ShortName property must be taken from dto.");
        Assertions.assertEquals(competition.getStartDate(), dto1.getStartDate(), "StartDate property must be taken from dto.");
    }

    @Test
    void updateCompetitionProperties() {
        competition.apply(new UpdateCompetitionProperties(dto2));

        Assertions.assertNotNull(competition.getId(), "Id must not be empty");
        Assertions.assertFalse(competition.getId().trim().isEmpty(), "Id must not be empty");
        Assertions.assertEquals(competition.getId(), dto2.getId(), "Id should not be taken from dto.");
        Assertions.assertEquals(competition.getName(), dto2.getName(), "Name property must be taken from dto.");
    }

    @Test
    void revokeCompetition() {
        competition.apply(new RevokeCompetition(dto2.getId()));
    }

    @Test
    void acceptCompetition() {
        competition.apply(new AcceptCompetition(dto2.getId()));
    }

    @Test
    void finalizeCompetition() {
        competition.apply(new FinalizeCompetition(dto2.getId()));
    }

    @Test
    void reopenCompetition() {
        competition.apply(new ReopenCompetition(dto2.getId()));
    }

    @Test
    void addAsset() {
        Assertions.assertTrue(competition.getAssets().isEmpty());
        String id = competition.apply(new AddAsset(dto2.getId(), assetDto));
        Assertions.assertEquals(1, competition.getAssets().size());
        AssetDto resultAssetDto = competition.getAssetDto(id);
        Assertions.assertEquals(id, resultAssetDto.getId());
        Assertions.assertEquals(assetDto.getName(), resultAssetDto.getName());
        Assertions.assertArrayEquals(assetDto.getData(), resultAssetDto.getData());
    }

    @Test
    void updateAsset() {
        String id = competition.apply(new AddAsset(dto2.getId(), assetDto));
        Assertions.assertEquals(1, competition.getAssets().size());

        assetDto.setId(id);
        assetDto.setName("protocol2.pdf");
        assetDto.setData(new byte[]{0x1, 0x2, 0x3, 0x4});

        competition.apply(new UpdateAsset(dto2.getId(), assetDto));
        Assertions.assertEquals(1, competition.getAssets().size());
        AssetDto resultAssetDto = competition.getAssetDto(id);
        Assertions.assertEquals(id, resultAssetDto.getId());
        Assertions.assertEquals(assetDto.getName(), resultAssetDto.getName());
        Assertions.assertArrayEquals(assetDto.getData(), resultAssetDto.getData());
    }

    @Test
    void removeAsset() {
        String id = competition.apply(new AddAsset(dto2.getId(), assetDto));
        Assertions.assertEquals(1, competition.getAssets().size());

        competition.apply(new RemoveAsset(dto2.getId(), id));
        Assertions.assertEquals(0, competition.getAssets().size());
    }

    @Test
    void validate() {
        competition.validate();
    }

    @Test
    void extractDto() {
        dto1.setId(dto2.getId());

        dto2 = competition.extractDto();

        Assertions.assertEquals(dto1.getName(), dto2.getName());
    }
}
