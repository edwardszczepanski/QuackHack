package com.edwardszczepanski.quackhack.Server.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.edwardszczepanski.quackhack.Server.Sprites.Player;

/**
 * Created by edwardszc on 1/15/16.
 */
public class WorldContactListener implements ContactListener {
    private Music collision = Gdx.audio.newMusic(Gdx.files.internal("bounce.wav"));

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		if (fixA.getUserData() instanceof Player || fixB.getUserData() instanceof Player) {
			Fixture objA = fixA.getUserData() instanceof Player ? fixA : fixB;
            ((Player) objA.getUserData()).setTouching(true);
            collision.play();
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() instanceof Player || fixB.getUserData() instanceof Player) {
            Fixture objA = fixA.getUserData() instanceof Player ? fixA : fixB;
            ((Player) objA.getUserData()).setTouching(false);
        }

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {

	}
}