package com.edwardszczepanski.quackhack.Server.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Screens.PlayScreen;

/**
 * Created by edwardszc on 1/15/16.
 */
public class Player extends Sprite{

    public enum State { FALLING, JUMPING, STANDING, RUNNING};
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private boolean touchingGround;
    private boolean isGoing;


    public Player (World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("duck_standing"));
        
        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        touchingGround = false;
        isGoing = false;

        defineMario();
        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16);
        // Now we do the bounds for how large to render it
        setBounds(0, 0, 16 / QuackHack.PPM, 16 / QuackHack.PPM);
        setRegion(marioStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
    }

    public void defineMario(){
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

}
