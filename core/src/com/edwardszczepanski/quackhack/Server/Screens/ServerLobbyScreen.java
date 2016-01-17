package com.edwardszczepanski.quackhack.Server.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.Net.NetListener;
import com.edwardszczepanski.quackhack.Server.Scenes.LobbyHUD;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;

/**
 * Created by edwardszc on 1/17/16.
 */
public class ServerLobbyScreen implements Screen, NetListener {
    private LobbyHUD hud;
    QuackHack game;

    public ServerLobbyScreen(QuackHack game){
        this.game = game;
        hud = new LobbyHUD(game);
        game.getServer().registerNetListener(this);
    }

    public void handleInput(float delta){
        if(Gdx.input.justTouched()){

        }
    }

    public void update(float delta){
        handleInput(delta);
        hud.update(delta);
        if(hud.getTime() == 0){
        	game.setScreen(new PlayScreen(game));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        hud.dispose();
    }

	public void reset() {
		hud.reset();
	}

	@Override
	public void netPing(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netPlayerConnected(Integer id, PlayerType type) {
		game.getServer().sendCommand(id, NetCommand.PLAYER_DIED);
	}

	@Override
	public void netPlayerDisconnected(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netPlayerJoin(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netPlayerDied(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netJump(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void netPlayerType(int id, PlayerType type) {
		// TODO Auto-generated method stub
		
	}

}
