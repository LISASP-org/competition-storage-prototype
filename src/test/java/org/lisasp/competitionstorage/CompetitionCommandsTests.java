package org.lisasp.competitionstorage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.DtoNotSpecifiedException;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDate;
import java.util.UUID;

class CompetitionCommandsTests {

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

    @BeforeEach
    void prepare() {
        dto = createDto("dto1");
    }

    private CompetitionDto dto;

    @Test
    void registerCompetition() {
        new RegisterCompetition(dto);
    }

    @Test
    void registerCompetitionWithoutDto() {
        Assertions.assertThrows(DtoNotSpecifiedException.class, () ->
                new RegisterCompetition(null));
    }

	@Test
	void updateCompetitionProperties() {
		new UpdateCompetitionProperties(dto);
	}

	@Test
	void updateCompetitionPropertiesWithoutDto() {
		Assertions.assertThrows(DtoNotSpecifiedException.class, () ->
				new UpdateCompetitionProperties(null));
	}

    @Test
    void finalizeCompetition() {
        new FinalizeCompetition("1");
    }

    @Test
    void finalizeCompetitionWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new FinalizeCompetition(null));
    }

    @Test
    void acceptCompetition() {
        new AcceptCompetition("1");
    }

    @Test
    void acceptCompetitionWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new AcceptCompetition(null));
    }

    @Test
    void reopenCompetition() {
        new ReopenCompetition("1");
    }

    @Test
    void reopenCompetitionWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new ReopenCompetition(null));
    }

    @Test
    void revokeCompetition() {
        new RevokeCompetition("1");
    }

    @Test
    void revokeCompetitionWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new RevokeCompetition(null));
    }

    @Test
    void addAsset() {
        new AddAsset("1", new AssetDto());
    }

    @Test
    void addAssetWithoutIdAndDto() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new AddAsset(null, null));
    }

    @Test
    void addAssetWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new AddAsset(null, new AssetDto()));
    }

    @Test
    void addAssetWithoutDto() {
        Assertions.assertThrows(DtoNotSpecifiedException.class, () ->
                new AddAsset("1", null));
    }

    @Test
    void updateAsset() {
        new UpdateAsset("1", new AssetDto());
    }

    @Test
    void updateAssetWithoutIdAndDto() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new UpdateAsset(null, null));
    }

    @Test
    void updateAssetWithoutId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new UpdateAsset(null, new AssetDto()));
    }

    @Test
    void updateAssetWithoutDto() {
        Assertions.assertThrows(DtoNotSpecifiedException.class, () ->
                new UpdateAsset("1", null));
    }


    @Test
    void removeAsset() {
        new RemoveAsset("1", "2");
    }

    @Test
    void removeAssetWithoutIds() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new RemoveAsset(null, null));
    }

    @Test
    void removeAssetWithoutCompetitionId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new RemoveAsset(null, "2"));
    }

    @Test
    void removeAssetWithoutAssetId() {
        Assertions.assertThrows(IdMissingException.class, () ->
                new RemoveAsset("1", null));
    }
}
