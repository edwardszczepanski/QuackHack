package com.edwardszczepanski.quackhack.Server.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Scenes.ParallaxBackground;
import com.edwardszczepanski.quackhack.Server.Scenes.ParallaxLayer;

/**
 * Created by edwardszc on 1/15/16.
 */
public class MenuScreen implements Screen {
    private OrthographicCamera gamecam;
    private ExtendViewport gamePort;

    private QuackHack game;
    private Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    private BitmapFont white, black;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Music menuMusic;
    private ParallaxBackground rbg;

    public MenuScreen(QuackHack game){
        this.game = game;
    }

    public void handleInput(float delta){

    }

    public void update(float delta){
        handleInput(delta);
    }

    @Override
    public void show() {
        gamecam = new OrthographicCamera();
        gamePort = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gamecam);

        stage = new Stage(gamePort, game.batch);
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("font/atlas.pack");
        skin = new Skin(Gdx.files.internal("font/menuSkin.json"), new TextureAtlas("font/atlas.pack"));
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("App Menu.wav"));
        menuMusic.setLooping(true);
        menuMusic.play();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BEBAS.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 60;

        font = generator.generateFont(parameter);

        heading = new Label("Game Full of Animals", new Label.LabelStyle(font, Color.WHITE));
        parameter.size = 32;
        font = generator.generateFont(parameter);

        // Creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = font;

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        buttonExit.pad(20);

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMusic.pause();
                stage.dispose();
                Gdx.input.setInputProcessor(stage);
                game.setScreen(new ServerLobbyScreen(game));
            }
        });
        buttonPlay.pad(20);

        // Assembling
        table.add(heading).padTop(135);
        table.getCell(heading).spaceBottom(18);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15);
        table.row();
        table.add(buttonExit);
        //table.debug(); // This enables all the debug lines
        stage.addActor(table);

        rbg = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(new TextureRegion(new Texture(Gdx.files.internal("blue_grass.png"))),new Vector2(0.5f, 0.5f),new Vector2(0, 300)),
                //new ParallaxLayer(atlas.findRegion("bg2"),new Vector2(1.0f,1.0f),new Vector2(0, 500)),
        }, 800, 480,new Vector2(150,0));
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1); // Color then opacity
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        rbg.render(delta);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        gamecam.update();

        stage.setViewport(gamePort);
        table.invalidateHierarchy();
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
        menuMusic.dispose();
    }
}
