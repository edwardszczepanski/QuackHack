package com.edwardszczepanski.quackhack.Server.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.edwardszczepanski.quackhack.Server.Sprites.InteractiveTileObject;
import com.edwardszczepanski.quackhack.Server.Sprites.Player;

/**
 * Created by edwardszc on 1/15/16.
 */
public class WorldContactListener implements ContactListener {


	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		if (fixA.getUserData() instanceof Player || fixB.getUserData() instanceof Player) {
			Fixture obj1 = fixA.getUserData() instanceof UserData ? fixA : fixB;
			if(obj1 != null) {
				if(((UserData) obj1.getUserData()).name == "foot") {
					((UserData) obj1.getUserData()).player.setTouching(true);
				} else if(((UserData) obj1.getUserData()).name == "right") {
					((UserData) obj1.getUserData()).player.setClimbing(true);
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();
		if (fixA.getUserData() instanceof UserData || fixB.getUserData() instanceof UserData) {
			Fixture obj1 = fixA.getUserData() instanceof UserData ? fixA : fixB;
			if(obj1 != null) {
				if(((UserData) obj1.getUserData()).name == "foot") {
					((UserData) obj1.getUserData()).player.setTouching(false);
				} else if(((UserData) obj1.getUserData()).name == "right") {
					((UserData) obj1.getUserData()).player.setClimbing(false);
				}
			}
		}

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}