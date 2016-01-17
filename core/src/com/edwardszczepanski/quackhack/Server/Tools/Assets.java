package com.edwardszczepanski.quackhack.Server.Tools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	private static TextureAtlas atlas;
	
	public static TextureAtlas getAtlas() {
		if(atlas == null) {
			atlas = new TextureAtlas("spritePack.txt");
		}
		return atlas;
	}
}
