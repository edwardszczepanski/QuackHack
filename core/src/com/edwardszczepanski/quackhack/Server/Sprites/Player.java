package com.edwardszczepanski.quackhack.Server.Sprites;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Screens.PlayScreen;
import com.edwardszczepanski.quackhack.Server.Tools.Assets;

/**
 * Created by edwardszc on 1/15/16.
 */
public class Player extends Sprite{

    public World world;
    public Body b2body;
    private TextureRegion playerRegion;
    private boolean touchingGround;
    private boolean isGoing;
    private PointLight pointLight;
    private Integer id;


    public Player (Integer id, World world, PlayScreen screen, PlayerType type){
        setBounds(0, 0, 128 / QuackHack.PPM, 128 / QuackHack.PPM);
        setRegion(Assets.getAtlas().findRegion(type.toString()));
        setOrigin(getHeight() / 2, getWidth() / 2);
        
        this.id = id;
        this.world = world;
        touchingGround = false;
        isGoing = false;

        definePlayer();
        // Now we do the bounds for how large to render it
        defineLights();
    }
    public void defineLights(){
        pointLight = new PointLight(PlayScreen.rayHandler, 150, Color.WHITE, 3f * 8*64/ QuackHack.PPM,0,0);
        pointLight.setSoftnessLength(0f);
        pointLight.attachToBody(b2body);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / QuackHack.PPM, 1500 / QuackHack.PPM); // Mario start position
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(64 / QuackHack.PPM);

        fdef.filter.categoryBits = QuackHack.MARIO_BIT;
        fdef.filter.maskBits = QuackHack.DEFAULT_BIT;
        fdef.friction = 0;
        fdef.density = 0.1f;

        fdef.shape = shape;
        b2body.createFixture(fdef);
        b2body.createFixture(fdef).setUserData(this);
    }

    public void setTouching(boolean input){
        touchingGround = input;
    }

    public boolean getTouching(){
        return touchingGround;
    }
    
    
    public void isGoing(boolean b) {
    	isGoing = b;
    }
    
    public boolean isGoing() {
    	return isGoing;
    }
    
    public enum PlayerType {
    	elephant, giraffe, hippo, monkey, panda, parrot, penguin, pig, rabbit, snake, boxCrate_double
    }

	public void destroy() {
		world.destroyBody(b2body);
	}
	
	public Integer getId() {
		return id;
	}
}
