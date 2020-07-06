package org.lisasp.competitionstorage.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.lisasp.competitionstorage.dto.*;
import org.lisasp.competitionstorage.logic.Competitions;
import org.lisasp.competitionstorage.logic.command.*;
import org.lisasp.competitionstorage.logic.exception.IdsNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController()
@RequestMapping("/api/competitions")
@RequiredArgsConstructor
public class CompetitionController {

    private final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    private final Competitions competitions;

    private final SimpMessageSendingOperations messagingTemplate;

    private final CommandGateway commandGateway;


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

    @GetMapping("/{id}/assets")
    List<AssetDto> findAssets(@PathVariable String id) {
        return competitions.findAssets(id);
    }

    @GetMapping("/{id}/assets/{assetId}")
    AssetDto findAssets(@PathVariable String id, @PathVariable String assetId) {
        return competitions.findAsset(id, assetId);
    }


    @PostMapping("")
    IdDto register(Principal principal, @RequestBody RegisterCompetitionDto dto) {
        String id = identifierFactory.generateIdentifier();
        commandGateway.send(new RegisterCompetition(id, dto.getShortName()));
        return new IdDto(id);
    }

    @PostMapping("/{id}/assets")
    IdDto addAsset(@RequestBody AddAssetDto asset, @PathVariable String id) {
        String assetId = identifierFactory.generateIdentifier();
        commandGateway.send(new AddAsset(id, assetId, asset.getFilename(), asset.getData()));
        return new IdDto(assetId);
    }

    @PutMapping("/{id}/assets/{assetId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void updateAsset(@RequestBody UpdateAssetDto asset, @PathVariable String id, @PathVariable String assetId) {
        commandGateway.send(new UpdateAsset(id, assetId, asset.getData()));
    }

    @DeleteMapping("/{id}/assets/{assetId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteAsset(@PathVariable String id, @PathVariable String assetId) {
        commandGateway.send(new RemoveAsset(id, assetId));
    }

    @PostMapping("/{id}/accept")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void accept(@PathVariable String id) {
        commandGateway.send(new AcceptCompetition(id));
    }

    @PostMapping("/{id}/finalize")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void finalize(@PathVariable String id) {
        commandGateway.send(new FinalizeCompetition(id));
    }

    @PostMapping("/{id}/reopen")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void reopen(@PathVariable String id) {
        commandGateway.send(new ReopenCompetition(id));
    }

    @PostMapping("/{id}/revoke")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void revoke(@PathVariable String id) {
        commandGateway.send(new RevokeCompetition(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void update(@RequestBody CompetitionDto competition, @PathVariable String id) {
        checkValidityOfIds(competition.getId(), id);
        commandGateway.send(new UpdateCompetitionProperties(id, competition.getName(), competition.getStartDate(), competition.getEndDate(), competition.getLocation(), competition.getCountry(), competition.getOrganization(), competition.getDescription()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void delete(@PathVariable String id) {
        // Not possible...
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
