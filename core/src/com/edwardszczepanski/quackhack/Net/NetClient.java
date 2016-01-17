package com.edwardszczepanski.quackhack.Net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetClient {
	private Client client = new Client();
	private Integer id = -1;

	public NetClient() {
		Kryo kryo = this.client.getKryo();
		kryo.register(Update.class);
		kryo.register(NetCommand.class);
		
		client.start();
		
		try {
			client.connect(5000, "10.111.111.60", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Receive Commands
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Update) {
					Update response = (Update)object;
					id = response.id;
					System.out.println(response.cmd);
				}
			}
		});
		
		Update response = new Update();
		response.id = id;
		response.cmd = NetCommand.PLAYER_CONNECTED;
		client.sendTCP(response);
	}

	public void sendCommand(NetCommand cmd) {
		Update request = new Update();
		request.id = id;
		request.cmd = cmd;
		client.sendTCP(request);
	}
}
