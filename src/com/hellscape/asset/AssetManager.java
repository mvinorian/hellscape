package com.hellscape.asset;

public class AssetManager {
    
    public static void load() {
        Tileset.load();
        EnemySprite.load();
        PlayerSprite.load();
        ObjectSprite.load();
    }
}
