package com.edwardszczepanski.quackhack;


import Net.NetClient;
import Net.NetServer;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.edwardszczepanski.quackhack.Server.Screens.PlayScreen;

public class QuackHack extends Game {
	SpriteBatch batch;
	NetClient client;
	NetServer server;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
		
		// Net Shit
		System.out.println("Good morning!");
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			// Run server
			server = new NetServer();
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
