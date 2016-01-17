package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Screens.PlayScreen;
import com.edwardszczepanski.quackhack.Server.Tools.Assets;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edwardszc on 1/16/16.
 */


public class LobbyDisplay implements Disposable{
    public Stage stage;
    private Table table;
    private Label heading;
    private Skin skin;

    public LobbyDisplay(final QuackHack game){

        stage = new Stage();

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Image one = new Image();
        Image two = new Image();
        Image three = new Image();
        Image four = new Image();
        Image five = new Image();
        Image six = new Image();
        Image seven = new Image();
        Image eight = new Image();
        Image nine = new Image();
        Image ten = new Image();

        one.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/elephant.png")))));
        two.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/giraffe.png")))));
        three.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/hippo.png")))));
        four.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/monkey.png")))));
        five.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/panda.png")))));
        six.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/parrot.png")))));
        seven.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/penguin.png")))));
        eight.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/pig.png")))));
        nine.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/rabbit.png")))));
        ten.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Round/snake.png")))));


        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BEBAS.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color.set(Color.BLACK);
        parameter.size = 50;
        font = generator.generateFont(parameter);


        heading = new Label("Pick a Character", new Label.LabelStyle(font, Color.WHITE));


        table.add(heading).expandX();
        table.row();
        table.add(one).expandX();
        table.add(two).expandX();
        table.add(three).expandX();
        table.add(four).expandX();
        table.add(five).expandX();
        table.row();
        table.add(six).expandX();
        table.add(seven).expandX();
        table.add(eight).expandX();
        table.add(nine).expandX();
        table.add(ten).expandX();
        table.debug(); // This enables all the debug lines
        stage.addActor(table);

    }

    @Override
    public void dispose() {

    }

}
