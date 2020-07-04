package org.lisasp.competitionstorage.logic.command;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;
import org.lisasp.competitionstorage.logic.exception.DtoNotSpecifiedException;

public class UpdateAsset extends DtoBase implements CompetitionCommand {

    private AssetDto dto;

    public UpdateAsset(String id, AssetDto dto) {
        super(id);
        this.dto = dto;
        validate();
    }

    private void validate() {
        if (dto == null) {
            throw new DtoNotSpecifiedException(getId());
        }
    }

    public String getAssetId() {
        return dto.getId();
    }

    public String getName() {
        return dto.getName();
    }

    public byte[] getData() {
        return dto.getData();
    }
}
