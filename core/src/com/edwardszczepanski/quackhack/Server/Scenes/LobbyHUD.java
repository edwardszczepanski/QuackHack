package com.edwardszczepanski.quackhack.Server.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.QuackHack;

/**
 * Created by edwardszc on 1/15/16.
 */

public class LobbyHUD implements Disposable {
    public Stage stage;
    private ExtendViewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private QuackHack game;
    // Now we create our widgets. Our widgets will be labels, essentially text, that allow us to display Game Information

    private Label countdownLabel;
    private Label timeLabel;
    private Label nameLabel;
    private Label playerLabel;

    public LobbyHUD(QuackHack game){
        this.game = game;
        this.reset();

        viewport = new ExtendViewport(960*4, 640*4, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BEBAS.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        parameter.size = 150*4;
        countdownLabel = new Label(String.format("%d", worldTimer), new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        parameter.size = 24*4;
        timeLabel = new Label("GAME STARTS IN ", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        parameter.size = 80*4;
        nameLabel = new Label("Please wait...", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));
        parameter.size = 30*4;
        playerLabel = new Label(String.format("%d ", game.getServer().getPlayers().length) + "  PLAYERS WAITING", new Label.LabelStyle(generator.generateFont(parameter), Color.WHITE));

        table.add(nameLabel).expandX().padTop(450);
        table.row();
        table.add(timeLabel).expandX().padTop(55);
        table.row();
        table.add(countdownLabel).expandX().pad(30);
        table.row();
        table.add(playerLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;


            countdownLabel.setText(String.format("%d", worldTimer));
            timeCount = 0;
        }
        playerLabel.setText(String.format("%d ", game.getServer().getPlayers().length) + "  PLAYERS WAITING");
    }
    public int getTime(){
        return worldTimer;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

	public void reset() {
        worldTimer = 30;
        timeCount = 0;
        score = 0;
	}
}
