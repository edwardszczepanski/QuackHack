package com.edwardszczepanski.quackhack.Server.Screens;

import java.util.ArrayList;
import java.util.HashMap;

import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Net.NetCommand;
import com.edwardszczepanski.quackhack.Net.NetListener;
import com.edwardszczepanski.quackhack.Server.Scenes.Hud;
import com.edwardszczepanski.quackhack.Server.Sprites.Box;
import com.edwardszczepanski.quackhack.Server.Sprites.Player;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;
import com.edwardszczepanski.quackhack.Server.Tools.B2WorldCreator;
import com.edwardszczepanski.quackhack.Server.Tools.WorldContactListener;
import com.esotericsoftware.kryonet.Connection;

/**
 * Created by edwardszc on 1/15/16.
 */

public class PlayScreen implements Screen, NetListener {
	private QuackHack game;

	private OrthographicCamera gamecam;
	private ExtendViewport gamePort;
	private Hud hud;
	
	private boolean isGoing = false;

	// Sprites
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();

	// Tiled Map Variables
	private TmxMapLoader maploader;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
    private ArrayList<Box> boxList = new ArrayList<Box>();

    //Box2d Lights
    public static RayHandler rayHandler;

	//Box2d variables
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private float maxX = 0;

	public PlayScreen(QuackHack game) {
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new ExtendViewport(QuackHack.V_WIDTH * 4 / QuackHack.PPM, QuackHack.V_HEIGHT * 4 / QuackHack.PPM, gamecam);
		hud = new Hud(game);
		maploader = new TmxMapLoader();

		map = maploader.load("ExtendedMap.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / QuackHack.PPM);

		gamecam.position.set(gamePort.getMinWorldWidth() / 2, gamePort.getMinWorldHeight() / 2, 0);
		world = new World(new Vector2(0, -100), true);
		b2dr = new Box2DDebugRenderer();
		new B2WorldCreator(world, map, this);
		world.setContactListener(new WorldContactListener());
		game.getServer().registerNetListener(this);

        rayHandler = rayHandlerGenerator();

		for(Connection c: game.getServer().getPlayers()) {
			System.out.println("New Player! id: "+c.getID());
			players.put(c.getID(), new Player(c.getID(), world, this, PlayerType.snake));
		}
	}

    public RayHandler rayHandlerGenerator(){
        RayHandler localRay = new RayHandler(world);
        RayHandler.useDiffuseLight(true);
        localRay.setAmbientLight(0.8f, 0.8f, 0.8f, 0.2f);
        localRay.setShadows(true);
        return localRay;
    }

	public void update(float delta) {
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			this.reset();
		}

		hud.update(delta);
        if(hud.getTime() == 0){
            isGoing = true;
        }

		world.step(1 / 60f, 6, 2);
		
		for(Player player: players.values()) {
			player.update(delta);
			if(isGoing) {
				if(player.b2body.getLinearVelocity().x < 36){
                    player.b2body.applyForce(new Vector2(60f, 0), player.b2body.getWorldCenter(), true);
                } else {
                	player.b2body.applyForce(new Vector2(-25f, 0), player.b2body.getWorldCenter(), true);
                }
			}
		}
		
        if(!boxList.isEmpty()){
            for(int i = 0; i < boxList.size(); ++i) {
                boxList.get(i).update();
            }
        }
        
		float minX = 999999;

		for(Player player: players.values()) {
			maxX = Math.max(maxX, player.b2body.getPosition().x);
			minX = Math.min(minX, player.b2body.getPosition().x);
		}
		
		float newWidth = (maxX-minX)*1.5f;
		float centerX = maxX-Math.min(gamecam.viewportWidth*0.75f, newWidth*0.25f);
		
		if(newWidth < gamecam.viewportWidth) {
			newWidth = gamecam.viewportWidth;
		}
		
		if(newWidth > gamecam.viewportWidth * 2) {
			newWidth = gamecam.viewportWidth * 2;
		}
		
		gamecam.zoom = newWidth/gamecam.viewportWidth;
		gamecam.position.x = centerX;
		gamecam.update();
		
		float camEdge = gamecam.position.x - gamecam.viewportWidth*gamecam.zoom/2;
		Array<Integer> des = new Array<Integer>();
		
		for(Player player: players.values()) {
			if(camEdge-(player.getWidth()*2) > player.getX() || player.getY() < -player.getHeight()) {
				des.add(player.getId());
			}
		}
		for(Integer p: des) {
			players.remove(p);
		}
		if(players.isEmpty()) {
			reset();
		}
		
		
		
		renderer.setView(gamecam);
        rayHandler.update();
	}

	@Override
	public void render(float delta) {
		update(delta);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// This is going to render the map
		renderer.render();

		//renderer our Box2DDebugLines
		//b2dr.render(world, gamecam.combined);

		game.batch.setProjectionMatrix(gamecam.combined);
		game.batch.begin();
		for(Player player: players.values()) {
			player.draw(game.batch);
		}
        if(!boxList.isEmpty()){
            for(int i = 0; i < boxList.size(); ++i) {
                boxList.get(i).draw(game.batch);
            }
        }

		game.batch.end();

        rayHandler.setCombinedMatrix(gamecam.combined.cpy().scl(1),
                gamecam.position.x, gamecam.position.y,
                gamecam.viewportWidth * gamecam.zoom,
                gamecam.viewportHeight * gamecam.zoom);

        rayHandler.render();

		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        if(hud.getTime() > 0){
            hud.stage.draw();
        }
	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);
	}

    public void addBoxList(Box box){
        boxList.add(box);
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
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void netPing(Integer id) {}
	
	@Override
	public void netJump(Integer id) {
		System.out.println(id);
		System.out.println(players.size());
		System.out.println(players.toString());
		
		Player p = players.get(id);
		if(p != null) {
	        p.b2body.applyLinearImpulse(new Vector2(0, 150f), p.b2body.getWorldCenter(), true);
		}
	}
	
	@Override
	public void netPlayerJoin(Integer id) {}
	
	@Override
	public void netPlayerDied(Integer id) {}

	@Override
	public void netPlayerConnected(Integer id) {
		if(!isGoing) {
			players.put(id, new Player(id, world, this, PlayerType.snake));
		}
	}

	@Override
	public void netPlayerDisconnected(Integer id) {}
	
	public void reset() {
		game.setScreen(new PlayScreen(game));
	}
}