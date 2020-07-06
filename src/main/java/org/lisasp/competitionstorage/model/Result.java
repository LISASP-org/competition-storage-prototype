package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.ResultDto;
import org.springframework.data.annotation.Id;

import java.util.Arrays;

public class Result {

    @Id
    @Setter
    @Getter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private byte[] data;

    public void validate() {
    }
}
