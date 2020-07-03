package org.lisasp.competitionstorage.logic.command;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.DtoBase;
import org.lisasp.competitionstorage.logic.base.CompetitionCommand;

public class AddAsset extends DtoBase implements CompetitionCommand {

    private AssetDto dto;

    public AddAsset(String id, AssetDto dto) {
        super(id);
        this.dto = dto;
    }

    public String getName() {
        return dto.getName();
    }

    public byte[] getData() {
        return dto.getData();
    }
}
