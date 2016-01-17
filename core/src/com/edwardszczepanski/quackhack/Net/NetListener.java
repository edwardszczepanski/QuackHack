package com.edwardszczepanski.quackhack.Net;

public interface NetListener {
	public void netPing(Integer id);

	public void netPlayerConnected(Integer id);
	public void netPlayerDisconnected(Integer id);
	
	public void netPlayerJoin(Integer id);
	public void netPlayerDied(Integer id);
	
	public void netJump(Integer id);
}
