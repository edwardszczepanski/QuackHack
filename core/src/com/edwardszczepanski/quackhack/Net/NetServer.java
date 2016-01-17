package com.edwardszczepanski.quackhack.Net;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class NetServer {
	private Server server = new Server();
	private Array<Integer> players = new Array<Integer>();
	private Array<NetListener> listeners = new Array<NetListener>();
	private int currentId = 0;

	public NetServer() {
		Kryo kryo = server.getKryo();
		kryo.register(Update.class);
		kryo.register(NetCommand.class);

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
					if(request.id == -1) {
						request.id = currentId++;
						System.out.println("New Connection! id: "+request.id);
					}
					
					System.out.println("command :: id: "+request.id + " cmd: "+ request.cmd.toString());

					switch(request.cmd) {
					case PLAYER_CONNECTED:
						players.add(request.id);
						System.out.println("players: "+players.size);
						
						Update response = new Update();
						response.id = request.id;
						response.cmd = NetCommand.PING;
						connection.sendTCP(response);

						for(NetListener listener: listeners) {
							listener.netPlayerConnected(request.id);
						}
						break;
					case PLAYER_DISCONNECTED:
						players.removeIndex(players.indexOf(request.id, true));

						for(NetListener listener: listeners) {
							listener.netPlayerDisconnected(request.id);
						}
						break;
						
					case JUMP:
						for(NetListener listener: listeners) {
							listener.netJump(request.id);
						}
						break;
					case MOVE_RIGHT:
						for(NetListener listener: listeners) {
							listener.netMoveRight(request.id);
						}
						break;

					case END_MOVE:
						for(NetListener listener: listeners) {
							listener.netEndMove(request.id);
						}
						break;

					case PING:
					default:
						for(NetListener listener: listeners) {
							listener.netPing(request.id);
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

	public Array<Integer> getPlayers() {
		return players;
	}
}
