package com.edwardszczepanski.quackhack.Server.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Scenes.Hud;


public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 27 + 1;

    public Coin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(QuackHack.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
