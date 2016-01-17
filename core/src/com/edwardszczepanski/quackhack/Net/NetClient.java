package com.edwardszczepanski.quackhack.Net;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.edwardszczepanski.quackhack.Server.Sprites.Player.PlayerType;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetClient {
	private Client client = new Client();
	private PlayerType type = PlayerType.snake;
	private Array<NetListener> listeners = new Array<NetListener>();

	public NetClient() {
		Kryo kryo = this.client.getKryo();
		kryo.register(Update.class);
		kryo.register(NetCommand.class);
		kryo.register(PlayerType.class);

		client.start();

		Timer.schedule(new Task(){
			@Override
			public void run() {
				if(!client.isConnected()) {
					System.out.println("Connecting...");
					try {
						client.connect(5000, "10.111.250.117", 54555, 54777);
					} catch (IOException e) {
						System.out.println("Failed to Connect.");
					}
					if(client.isConnected()) {
						System.out.println("Connected!");

						Update response = new Update();
						response.cmd = NetCommand.PLAYER_CONNECTED;
						client.sendTCP(response);
					}
				}
			}
		}, 0, 5);

		// Receive Commands
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Update) {
					Update request = (Update)object;
					int id = connection.getID();
					
					System.out.println("command :: id: "+id + " cmd: "+ request.cmd.toString());

					switch(request.cmd) {
					case PLAYER_JOIN:
						for(NetListener listener: listeners) {
							listener.netPlayerJoin(id);
						}
						break;
						
					case PLAYER_DIED:
						for(NetListener listener: listeners) {
							listener.netPlayerDied(id);
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

	public void sendCommand(NetCommand cmd) {
		Update request = new Update();
		request.type = type;
		request.cmd = cmd;
		client.sendTCP(request);
	}

	public void setType(PlayerType type) {
		this.type = type;
	}
}
