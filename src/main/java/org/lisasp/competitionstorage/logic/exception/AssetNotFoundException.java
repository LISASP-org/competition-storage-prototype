package org.lisasp.competitionstorage.logic.exception;

public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String id, String assetId) {
        super(String.format("Asset \"%s\" for Competition \"%s\" not found.", assetId, id));
    }

    public AssetNotFoundException(String id, String assetId, String name) {
        super(String.format("Asset \"%s\" with name \"%s\" for Competition \"%s\" not found.", assetId, name, id));
    }
}
