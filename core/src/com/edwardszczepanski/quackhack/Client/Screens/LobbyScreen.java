package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;

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
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                    System.out.println(4);
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                    System.out.println(3);
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                    System.out.println(2);
                }
                else{
                    System.out.println(1);
                }
            }
            else{
                if(Gdx.input.getX() > 4*Gdx.graphics.getWidth()/5){
                    System.out.println(10);
                }
                else if (Gdx.input.getX() > 3*Gdx.graphics.getWidth()/5){
                    System.out.println(9);
                }
                else if (Gdx.input.getX() > 2*Gdx.graphics.getWidth()/5){
                    System.out.println(8);
                }
                else if (Gdx.input.getX() > Gdx.graphics.getWidth()/5){
                    System.out.println(7);
                }
                else{
                    System.out.println(6);
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
