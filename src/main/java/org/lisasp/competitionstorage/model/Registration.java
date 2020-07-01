package org.lisasp.competitionstorage.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Registration {

    @Id
    @Getter
    private String id;

    @Getter
    @Setter
    private String token;

    @Getter
    @Setter
    private String competitionId;

    @Getter
    @Setter
    private List<Asset> assets;

    @Getter
    @Setter
    private List<Result> results;
}
