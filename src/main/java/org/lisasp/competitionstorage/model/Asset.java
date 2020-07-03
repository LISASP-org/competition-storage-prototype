package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Asset {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private byte[] data;

    public AssetDto extractDto() {
        AssetDto dto = new AssetDto();
        dto.setId(id);
        dto.setName(name);
        if (data != null) {
            dto.setData(Arrays.copyOf(data, data.length));
        } else {
            dto.setData(null);
        }
        return dto;
    }
}
