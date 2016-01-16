package com.edwardszczepanski.quackhack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.quackhack.Server.Screens.MenuScreen;
import com.edwardszczepanski.quackhack.Server.Screens.PlayScreen;

/**
 * Created by edwardszc on 1/15/16.
 */

public class QuackHack extends Game {
	public static final float PPM = 100;
	public static final float V_WIDTH = 640;
	public static final float V_HEIGHT = 480;

	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;


	public SpriteBatch batch;
    private QuackHack game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
        game = this;
		setScreen(new MenuScreen(game));
	}

	@Override
	public void render () {
		super.render();
	}
}
