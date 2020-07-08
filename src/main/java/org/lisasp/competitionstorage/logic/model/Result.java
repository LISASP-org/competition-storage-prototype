package org.lisasp.competitionstorage.logic.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

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