package org.lisasp.competitionstorage.rest;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.lisasp.competitionstorage.logic.Competitions;
import org.lisasp.competitionstorage.logic.exception.AssetNotFoundException;
import org.lisasp.competitionstorage.model.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/downloads", produces = "application/pdf")
public class DownloadController {

    private final Competitions competitions;

    @Autowired
    DownloadController(Competitions competitions) {
        this.competitions = competitions;
    }

    @GetMapping("")
    List<CompetitionDto> find(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        if ("".equals(name)) {
            return competitions.findAll();
        }
        return competitions.findByName(name);
    }

    @GetMapping("/{id}/assets/{assetId}/{name}")
    byte[] findAssets(@PathVariable String id, @PathVariable String assetId, @PathVariable String name) {
        AssetDto asset = competitions.findAsset(id, assetId);
        if (!asset.getName().equalsIgnoreCase(name.trim())) {
            throw new AssetNotFoundException(id, assetId, name);
        }
        return asset.getData();
    }
}
