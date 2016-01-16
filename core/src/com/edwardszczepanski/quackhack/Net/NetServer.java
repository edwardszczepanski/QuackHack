package com.edwardszczepanski.quackhack.Net;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
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
					for(NetListener listener: listeners) {
						switch(request.cmd) {
						case JUMP:
							listener.netJump();
							break;
						case MOVE_RIGHT:
							listener.netMoveRight();
							break;
						case PING:
						default:
							listener.netPing();
							break;
						}
					}

					Update response = new Update();
					response.cmd = NetCommand.PING;
					connection.sendTCP(response);
				}
			}
		});
	}

	public void registerNetListener(NetListener listener) {
		listeners.add(listener);
	}
}
