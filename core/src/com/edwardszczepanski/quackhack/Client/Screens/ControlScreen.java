package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.Net.NetListener;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;

/**
 * Created by edwardszc on 1/15/16.
 */

public class ControlScreen implements Screen, NetListener {
    private MobileDisplay hud;
    QuackHack game;

    public ControlScreen(QuackHack game){
        this.game = game;
        hud = new MobileDisplay(game); 
        
        game.getClient().registerNetListener(this);
    }

    public void handleInput(float delta){
        if(Gdx.input.justTouched()){
            game.getClient().sendCommand(NetCommand.JUMP);
        }
    }

    public void update(float delta){
        handleInput(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 1, 1, 1); // Color then opacity
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
    
	@Override
	public void netPing(Integer id) {}

	@Override
	public void netPlayerConnected(Integer id, PlayerType type) {}

	@Override
	public void netPlayerDisconnected(Integer id) {}

	@Override
	public void netPlayerJoin(Integer id) {}

	@Override
	public void netPlayerDied(Integer id) {
		 Gdx.app.postRunnable(new Runnable() {
	         @Override
	         public void run() {
	     		game.setScreen(game.getLobbyScreen());
	         }
		 });
	}

	@Override
	public void netJump(Integer id) {}

	@Override
	public void netPlayerType(int id, PlayerType type) {}
}
