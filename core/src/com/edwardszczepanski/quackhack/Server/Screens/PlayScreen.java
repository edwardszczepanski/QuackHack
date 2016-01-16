package com.edwardszczepanski.quackhack.Server.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Scenes.Hud;
import com.edwardszczepanski.quackhack.Server.Sprites.Player;
import com.edwardszczepanski.quackhack.Server.Tools.B2WorldCreator;
import com.edwardszczepanski.quackhack.Server.Tools.WorldContactListener;

/**
 * Created by edwardszc on 1/15/16.
 */

public class PlayScreen implements Screen {
    private QuackHack game;
    private TextureAtlas atlas;

    private OrthographicCamera gamecam;
    private ExtendViewport gamePort;
    private Hud hud;

    // Sprites
    private Player player;
    private BitmapFont font12;

    // Tiled Map Variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(QuackHack game) {
        atlas = new TextureAtlas("mario_and_enemies.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new ExtendViewport(QuackHack.V_WIDTH * 2.5f / QuackHack.PPM, QuackHack.V_HEIGHT * 2.5f / QuackHack.PPM, gamecam);
        hud = new Hud(game.batch);
        maploader = new TmxMapLoader();
        //map = maploader.load("TiledMap/tiledTest.tmx");
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / QuackHack.PPM);


        gamecam.position.set(gamePort.getMinWorldWidth() / 2, gamePort.getMinWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        player = new Player(world, this);
        world.setContactListener(new WorldContactListener());
    }

    public void handleInput(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            gamecam.position.y += 10 / QuackHack.PPM;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            gamecam.position.y -= 10 / QuackHack.PPM;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            gamecam.position.x += 10 / QuackHack.PPM;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            gamecam.position.x -= 10 / QuackHack.PPM;
        }
        /*if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        */
    }

    public void update(float delta) {
        handleInput(delta);
        player.update(delta);
        hud.update(delta);
        world.step(1 / 60f, 6, 2);
        //gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // This is going to render the map
        renderer.render();

        //renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    @Override
    public void show() {

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
}