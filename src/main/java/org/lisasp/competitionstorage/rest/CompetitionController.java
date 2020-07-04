package org.lisasp.competitionstorage.rest;

import org.lisasp.competitionstorage.dto.AssetDto;
import org.lisasp.competitionstorage.logic.Competitions;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.IdsNotValidException;
import org.lisasp.competitionstorage.dto.CompetitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController()
@RequestMapping("/competitions")
public class CompetitionController {

    private final Competitions competitions;

    @Autowired
    CompetitionController(Competitions competitions) {
        this.competitions = competitions;
    }

    @GetMapping("")
    List<CompetitionDto> find(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        if ("".equals(name)) {
            return competitions.findAll();
        }
        return competitions.findByName(name);
    }

    @GetMapping("/{id}")
    CompetitionDto findById(@PathVariable String id) {
        return competitions.find(id);
    }

    @PostMapping("")
    CompetitionDto register(@RequestBody CompetitionDto competition) {
        String id = competitions.apply(new RegisterCompetition(competition));
        return competitions.find(id);
    }

    @PostMapping("/{id}/assets")
    AssetDto addAsset(@RequestBody AssetDto asset, @PathVariable String id) {
        String assetId = competitions.apply(new AddAsset(id, asset));
        return competitions.findAsset(id, assetId);
    }

    @PutMapping("/{id}/assets/{assetId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void updateAsset(@RequestBody AssetDto asset, @PathVariable String id, @PathVariable String assetId) {
        competitions.apply(new UpdateAsset(id, asset));
    }

    @DeleteMapping("/{id}/assets/{assetId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteAsset(@PathVariable String id, @PathVariable String assetId) {
        competitions.removeAsset(new RemoveAsset(id, assetId));
    }

    @GetMapping("/{id}/assets")
    List<AssetDto> findAssets(@PathVariable String id) {
        return competitions.findAssets(id);
    }

    @GetMapping("/{id}/assets/{assetId}")
    AssetDto findAssets(@PathVariable String id, @PathVariable String assetId) {
        return competitions.findAsset(id, assetId);
    }

    @PostMapping("/{id}/accept")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void accept(@PathVariable String id) {
        competitions.apply(new AcceptCompetition(id));
    }

    @PostMapping("/{id}/finalize")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void finalize(@PathVariable String id) {
        competitions.apply(new FinalizeCompetition(id));
    }

    @PostMapping("/{id}/reopen")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void reopen(@PathVariable String id) {
        competitions.apply(new ReopenCompetition(id));
    }

    @PostMapping("/{id}/revoke")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void revoke(@PathVariable String id) {
        competitions.apply(new RevokeCompetition(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void update(@RequestBody CompetitionDto competition, @PathVariable String id) {
        checkValidityOfIds(competition.getId(), id);
        competitions.apply(new UpdateCompetition(competition));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void delete(@PathVariable String id) {
        competitions.delete(id);
    }

    private void checkValidityOfIds(String id1, String id2) {
        if (!areValid(id1, id2)) {
            throw new IdsNotValidException(id1, id2);
        }
    }

    private boolean areValid(String id1, String id2) {
        if (isNullOrEmpty(id1)) {
            return false;
        }
        if (isNullOrEmpty(id2)) {
            return false;
        }
        return id1.equals(id2);
    }

    private boolean isNullOrEmpty(String id) {
        if (id == null) {
            return true;
        }
        return id.trim().isEmpty();
    }
}
