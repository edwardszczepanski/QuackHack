package com.edwardszczepanski.quackhack.Server.Screens;

/**
 * Created by edwardszc on 1/16/16.
 */


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;


public class SplashScreen implements Screen{
    private QuackHack game;
    private Sprite splash;
    private Sprite splash2;
    private TweenManager tweenManager;

    public SplashScreen(QuackHack game){
        this.game = game;
    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched()){
            game.setScreen(new MenuScreen(game));
        }
    }

    public void update(float delta){
        if(System.nanoTime() % 1000000 > 500000){
            splash = new Sprite(new Texture(Gdx.files.internal("inferno_duck.png")));
        }
        else{
            splash = new Sprite(new Texture(Gdx.files.internal("inferno_duck_2.png")));
        }

        handleInput(delta);
    }

    @Override
    public void show() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splash = new Sprite(new Texture(Gdx.files.internal("inferno_duck.png")));
        //splash.setPosition(Gdx.graphics.getWidth()/2 - splash.getWidth() / 2, Gdx.graphics.getHeight()/2 - splash.getHeight() /2);

        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1, 15f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new MenuScreen(game));
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        update(delta);
        tweenManager.update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();

        //splash.draw(game.batch);
        game.batch.draw(splash, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.end();
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
        game.batch.dispose();
        splash.getTexture().dispose();
    }
}

