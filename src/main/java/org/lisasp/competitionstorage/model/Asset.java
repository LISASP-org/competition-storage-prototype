package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

public class Asset {

    @Id
    @Getter
    private String id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private byte[] data;
}
