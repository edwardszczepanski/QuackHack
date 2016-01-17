package com.edwardszczepanski.quackhack.Server.Tools;

import com.edwardszczepanski.quackhack.Server.Sprites.Player;

public class UserData {
	public Player player;
	public String name;
	
	public UserData(Player player, String name) {
		this.player = player;
		this.name = name;
	}
}
