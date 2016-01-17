package com.edwardszczepanski.quackhack.Net;

import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;

public interface NetListener {
	public void netPing(Integer id);

	public void netPlayerConnected(Integer id, PlayerType type);
	public void netPlayerDisconnected(Integer id);
	
	public void netPlayerJoin(Integer id);
	public void netPlayerDied(Integer id);
	
	public void netJump(Integer id);

	public void netPlayerType(int id, PlayerType type);
}
