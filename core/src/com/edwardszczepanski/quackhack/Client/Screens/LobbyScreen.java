package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;

/**
 * Created by edwardszc on 1/15/16.
 */

public class LobbyScreen implements Screen {
    private LobbyDisplay hud;
    QuackHack game;

    public LobbyScreen(QuackHack game){
        this.game = game;
        hud = new LobbyDisplay(game);
    }

    public void handleInput(float delta){
        if(Gdx.input.justTouched()){
            if(Gdx.input.getY() < Gdx.graphics.getHeight() /2){ // This is on the top row
                if(Gdx.input.getX() > 4*Gdx.graphics.getWidth()/5){
                    System.out.println(5);
                	game.getClient().setType(PlayerType.panda);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                    System.out.println(4);
                	game.getClient().setType(PlayerType.monkey);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                    System.out.println(3);
                	game.getClient().setType(PlayerType.hippo);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                    System.out.println(2);
                	game.getClient().setType(PlayerType.giraffe);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else{
                	System.out.println(1);
                	game.getClient().setType(PlayerType.elephant);
                    game.getClient().sendCommand(NetCommand.PING);
                }
            }
            else{
                if(Gdx.input.getX() > 4*Gdx.graphics.getWidth()/5){
                    System.out.println(10);
                	game.getClient().setType(PlayerType.snake);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                    System.out.println(9);
                	game.getClient().setType(PlayerType.rabbit);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                    System.out.println(8);
                	game.getClient().setType(PlayerType.pig);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                    System.out.println(7);
                	game.getClient().setType(PlayerType.penguin);
                    game.getClient().sendCommand(NetCommand.PING);
                }
                else{
                    System.out.println(6);
                	game.getClient().setType(PlayerType.parrot);
                    game.getClient().sendCommand(NetCommand.PING);
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

}
