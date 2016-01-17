package com.edwardszczepanski.quackhack.Client.Screens;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;

/**
 * Created by edwardszc on 1/15/16.
 */

public class ControlScreen implements Screen {
    private MobileDisplay hud;
    QuackHack game;

    public ControlScreen(QuackHack game){
        this.game = game;
        hud = new MobileDisplay(game);
    }

    public void handleInput(float delta){
        if(Gdx.input.justTouched()){
            game.getClient().sendCommand(NetCommand.MOVE_RIGHT);
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

}
