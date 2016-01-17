package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edwardszc on 1/15/16.
 */

class TouchInfo {
    public float touchX = 0;
    public float touchY = 0;
    public boolean touched = false;
}

public class ControlScreen implements Screen, InputProcessor {
	private boolean isMoveTouched = false;
	private boolean isJumpTouched = false;
    private MobileDisplay hud;
    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();
    QuackHack game;

    public ControlScreen(QuackHack game){
        this.game = game;
        hud = new MobileDisplay(game);
        Gdx.input.setInputProcessor(this);

        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
    }

    public void handleInput(float delta){
        //if(Gdx.input.)
    }

    public void update(float delta){
        handleInput(delta);
        boolean touchAtAllJ = false;
        boolean touchAtAllM = false;

        for(int i = 0; i < 5; i++){
            if(touches.get(i).touched){
                if(touches.get(i).touchX > Gdx.graphics.getWidth() /2){
                    if(!isMoveTouched) {
                        game.getClient().sendCommand(NetCommand.MOVE_RIGHT);
                    }
                    touchAtAllM = true;
                	isMoveTouched = true;
                }
                else{
                    if(!isJumpTouched) {
                        game.getClient().sendCommand(NetCommand.JUMP);
                    }
                	touchAtAllJ = true;
                	isJumpTouched = true;
                }
            } else {
            	if(!touchAtAllJ) {
                	isJumpTouched = false;
            	}
            	if(!touchAtAllM) {
            		isMoveTouched = false;
            	}
            }
        }
    	touchAtAllJ = false;
    	touchAtAllM = false;
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            if(touches.get(pointer).touchX > Gdx.graphics.getWidth() /2){
                System.out.println("lolUp");
                game.getClient().sendCommand(NetCommand.END_MOVE);
            }
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
