package com.edwardszczepanski.quackhack.Server.Sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Scenes.Hud;

/**
 * Created by edwardszc on 1/15/16.
 */
public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(QuackHack.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(QuackHack.DESTROYED_BIT);
        getCell().setTile(null);
        Hud.addScore(200);
    }
}