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
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;
    private boolean touchingGround;
    private boolean isGoing;


    public Player (World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("duck_standing"));

        this.world = world;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        touchingGround = true;
        isGoing = false;


        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; ++i){
            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 0 + 11, 16, 16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();

        for(int i = 4; i < 6; ++i){
            frames.add(new TextureRegion(getTexture(), 1 + i * 16, 0 + 11, 16, 16));
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();


        defineMario();
        marioStand = new TextureRegion(getTexture(), 1, 11, 16, 16);
        // Now we do the bounds for how large to render it
        setBounds(0, 0, 16 / QuackHack.PPM, 16 / QuackHack.PPM);
        setRegion(marioStand);
    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }

        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0; // Weird syntax here
        previousState = currentState;
        return region;

    }

    public State getState(){
        if (b2body.getLinearVelocity().y > 0 || b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING){
            return State.JUMPING;
        }
        else if (b2body.getLinearVelocity().y < 0){
            return State.FALLING;
        }
        else if (b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }
        else {
            return State.STANDING;
        }
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

        fdef.shape = shape;
        b2body.createFixture(fdef);

        // This is generating a head sensor
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-30 / QuackHack. PPM, 64 / QuackHack.PPM), new Vector2(30 / QuackHack. PPM, 64 / QuackHack.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");

        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-30 / QuackHack. PPM, -64 / QuackHack.PPM), new Vector2(30 / QuackHack. PPM, -64 / QuackHack.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;

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
