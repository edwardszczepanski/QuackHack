package com.edwardszczepanski.quackhack.Client.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.QuackHack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by edwardszc on 1/16/16.
 */


public class MobileDisplay implements Disposable{
    public Stage stage;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private Skin skin;
    private BitmapFont white, black;
    private TextureAtlas atlas;

    public MobileDisplay(final QuackHack game){

        stage = new Stage();
        atlas = new TextureAtlas("font/atlas.pack");
        skin = new Skin(Gdx.files.internal("font/menuSkin.json"), new TextureAtlas("font/atlas.pack"));
        table = new Table(skin);
        table.setBounds(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white32.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("font/black32.fnt"), false);

        // Creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("RUN", textButtonStyle);

        buttonExit.pad(300);

        buttonPlay = new TextButton("JUMP", textButtonStyle);

        buttonPlay.pad(300);

        // Creating heading
        heading = new Label("QuackHack", new Label.LabelStyle(new BitmapFont(Gdx.files.internal("font/white64.fnt"), false), Color.WHITE));

        // Assembling
        table.add(heading);
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(buttonPlay);
        table.getCell(buttonPlay).spaceBottom(15);
        table.add(buttonExit);
        table.debug(); // This enables all the debug lines
        stage.addActor(table);


    }

    @Override
    public void dispose() {

    }

}
