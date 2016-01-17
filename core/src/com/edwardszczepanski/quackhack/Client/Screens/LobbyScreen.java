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

public class LobbyScreen implements Screen, NetListener {
    private LobbyDisplay hud;
    QuackHack game;

    public LobbyScreen(QuackHack game){
        this.game = game;
        game.getClient().registerNetListener(this);
        hud = new LobbyDisplay(game);
    }

    public void handleInput(float delta){
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY() < Gdx.graphics.getHeight() /2){ // This is on the top row
                if(Gdx.input.getX() > 4*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.panda);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: PANDA");
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.monkey);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: MONKEY");
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.hippo);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: HIPPO");
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.giraffe);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: GIRAFFE");
                }
                else{
                	game.getClient().setType(PlayerType.elephant);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: ELEPHANT");

                }
            }
            else{
                if(Gdx.input.getX() > 4*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.snake);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: SNAKE");
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.rabbit);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: RABBIT");
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.pig);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: PIG");
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                	game.getClient().setType(PlayerType.penguin);

                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: PENGUIN");
                }
                else{
                    System.out.println(6);
                	game.getClient().setType(PlayerType.parrot);
                    game.getClient().sendCommand(NetCommand.PLAYER_TYPE);
                    hud.heading.setText("CHOOSE CHARACTER  CURRENT: PARROT");
                }
            }
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
	public void netPlayerJoin(Integer id) {
		 Gdx.app.postRunnable(new Runnable() {
	         @Override
	         public void run() {
	     		game.setScreen(game.getControlScreen());
	         }
		 });
	}

	@Override
	public void netPlayerDied(Integer id) {}

	@Override
	public void netJump(Integer id) {}

	@Override
	public void netPlayerType(int id, PlayerType type) {}

}
