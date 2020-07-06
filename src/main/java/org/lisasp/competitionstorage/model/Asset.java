package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.logic.event.AssetAdded;
import org.lisasp.competitionstorage.logic.event.AssetUpdated;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class Asset {

    @Getter
    @NotNull
    private String name;

    @Getter
    @NotNull
    private byte[] data;

    public Asset(AssetAdded event) {
        name = event.getFilename();
        data = event.getData();
        validate();
    }

    public void on(AssetUpdated event) {
        data = event.getData();
        validate();
    }

    public void validate() {
        if (data == null) {
            data = new byte[0];
        }
    }
}
