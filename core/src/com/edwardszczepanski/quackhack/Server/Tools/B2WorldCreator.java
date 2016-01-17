package com.edwardszczepanski.quackhack.Server.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.edwardszczepanski.quackhack.QuackHack;

/**
 * Created by edwardszc on 1/15/16.
 */
public class B2WorldCreator {

	public B2WorldCreator(World world, TiledMap map){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object: map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/ QuackHack.PPM, (rect.getY() + rect.getHeight()/2)/QuackHack.PPM); // I don't follow the math
			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2 / QuackHack.PPM, rect.getHeight() / 2 / QuackHack.PPM);
			fdef.shape = shape;
			fdef.friction = 0.4f;
			body.createFixture(fdef);
		}
		
		for(MapObject object: map.getLayers().get("Dynamic").getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyDef.BodyType.DynamicBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/ QuackHack.PPM, (rect.getY() + rect.getHeight()/2)/QuackHack.PPM); // I don't follow the math
			body = world.createBody(bdef);

			shape.setAsBox(rect.getWidth() / 2 / QuackHack.PPM, rect.getHeight() / 2 / QuackHack.PPM);
			fdef.shape = shape;
			fdef.friction = 0.4f;
			body.createFixture(fdef);
		}
	}

}