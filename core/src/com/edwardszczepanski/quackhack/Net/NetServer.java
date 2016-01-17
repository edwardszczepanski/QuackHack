package com.edwardszczepanski.quackhack.Net;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class NetServer {
	private Server server = new Server();
	private Array<NetListener> listeners = new Array<NetListener>();	

	public NetServer() {
		Kryo kryo = server.getKryo();
		kryo.register(Update.class);
		kryo.register(NetCommand.class);
		kryo.register(PlayerType.class);
		
		server.start();

		try {
			server.bind(54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Receive Commands
		server.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Update) {
					Update request = (Update)object;
					int id = connection.getID();
					
					System.out.println("command :: id: "+id + " cmd: "+ request.cmd.toString() + " type: "+request.type.toString());

					switch(request.cmd) {
					case PLAYER_CONNECTED:
						System.out.println("players: "+server.getConnections().length);
						
						Update response = new Update();
						response.cmd = NetCommand.PING;
						connection.sendTCP(response);

						for(NetListener listener: listeners) {
							listener.netPlayerConnected(id);
						}
						break;
					case PLAYER_DISCONNECTED:
						for(NetListener listener: listeners) {
							listener.netPlayerDisconnected(id);
						}
						break;
						
					case PLAYER_JOIN:
						for(NetListener listener: listeners) {
							listener.netPlayerJoin(id);
						}
						break;
						
					case JUMP:
						for(NetListener listener: listeners) {
							listener.netJump(id);
						}
						break;

					case PING:
					default:
						for(NetListener listener: listeners) {
							listener.netPing(id);
						}
						break;
					}
				}
			}
		});
	}

	public void registerNetListener(NetListener listener) {
		listeners.add(listener);
	}

	public Connection[] getPlayers() {
		return server.getConnections();
	}

	public void sendCommand(int id, NetCommand cmd) {
		for(Connection c : server.getConnections()) {
			if(c.getID() == id) {
				Update response = new Update();
				response.cmd = cmd;
				c.sendTCP(response);
				break;
			}
		}
	}
}
