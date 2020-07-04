package org.lisasp.competitionstorage.logic.command;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;
import org.lisasp.competitionstorage.logic.exception.IdMissingException;

public class RemoveAsset extends DtoBase implements CompetitionCommand {

    @Getter
    private String assetId;

    public RemoveAsset(String id, String assetId) {
        super(id);
        this.assetId = assetId;
        validate();
    }

    private void validate() {
        if (assetId == null || assetId.trim().isEmpty()) {
            throw new IdMissingException(getId());
        }
    }
}
