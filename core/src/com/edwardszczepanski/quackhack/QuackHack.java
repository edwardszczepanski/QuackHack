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
	public static final float V_WIDTH = 960;
	public static final float V_HEIGHT = 540;

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
		batch = new SpriteBatch();
		
		// Net Shit
		System.out.println("Good morning!");
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			// Run server
			server = new NetServer();
		} else {
			// Run client
			client = new NetClient();
		}
        game = this;
		setScreen(new MenuScreen(game));
	}

	@Override
	public void render () {
		super.render();
	}
}
