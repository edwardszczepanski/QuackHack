package com.edwardszczepanski.quackhack.Net;

public interface NetListener {
	public void netPing(Integer id);

	public void netPlayerConnected(Integer id);
	public void netPlayerDisconnected(Integer id);
	
	public void netJump(Integer id);
	public void netMoveRight(Integer id);

	public void netEndMove(Integer id);
}
