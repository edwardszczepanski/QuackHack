package com.edwardszczepanski.quackhack;

import Net.NetClient;
import Net.NetServer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.quackhack.Server.Screens.MenuScreen;

/**
 * Created by edwardszc on 1/15/16.
 */

public class QuackHack extends Game {
	public static final float PPM = 100;
	public static final float V_WIDTH = 320;
	public static final float V_HEIGHT = 240;

	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	
	private NetClient client;
	private NetServer server;

	public SpriteBatch batch;
    private QuackHack game;
	
	@Override
	public void create () {		
		// Net Shit
		System.out.println("Good morning!");
        game = this;
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			// Run server
			server = new NetServer();
			batch = new SpriteBatch();
			setScreen(new MenuScreen(game));
		} else {
			// Run client
			client = new NetClient();
		}
	}

	@Override
	public void render () {
		super.render();
	}
}
