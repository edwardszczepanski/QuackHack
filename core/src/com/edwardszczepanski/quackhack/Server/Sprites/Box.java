package com.edwardszczepanski.quackhack.Server.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.quackhack.QuackHack;
import com.edwardszczepanski.quackhack.Server.Tools.Assets;

/**
 * Created by edwardszc on 1/17/16.
 */
public class Box extends Sprite {
    BodyDef bdef = new BodyDef();
    PolygonShape shape = new PolygonShape();
    FixtureDef fdef = new FixtureDef();
    Body body;
    private TextureRegion boxRegion;

    public Box(World world, MapObject object){
        super(Assets.getAtlas().findRegion("boxCrate_double"));
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set((rect.getX()+rect.getWidth()/2)/ QuackHack.PPM, (rect.getY() + rect.getHeight()/2)/QuackHack.PPM); // I don't follow the math
        body = world.createBody(bdef);
                
        shape.setAsBox(rect.getWidth() / 2 / QuackHack.PPM, rect.getHeight() / 2 / QuackHack.PPM);
        fdef.shape = shape;
        fdef.friction = 0.4f;
        fdef.density = 0.1f;
        body.createFixture(fdef);

        boxRegion = new TextureRegion(getTexture(), 0, 0, 128, 128);
        setBounds(0,0, 128 / QuackHack.PPM, 128 / QuackHack.PPM);
        setRegion(boxRegion);
        setOrigin(getHeight()/2, getWidth()/2);
    }

    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getWidth() / 2);
        setRotation((float) ((body.getAngle()/Math.PI)*180));
    }
}
