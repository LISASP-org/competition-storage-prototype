package org.lisasp.competitionstorage.logic;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Competitions {

    public CompetitionDto find(String id) {
        return null;
    }

    public List<CompetitionDto> findByName(String name) {
        return new ArrayList<>();
    }

    public List<CompetitionDto> findAll() {
        return new ArrayList<>();
    }

    public AssetDto findAsset(String competitionId, String assetId) {
        return null;
    }

    public List<AssetDto> findAssets(String id) {
        return new ArrayList<>();
    }
}
