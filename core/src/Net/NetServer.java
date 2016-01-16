package Net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class NetServer {
	private Server server = new Server();

	public NetServer() {
		Kryo kryo = server.getKryo();
		kryo.register(Update.class);
		
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
					System.out.println(request.cmd);

					Update response = new Update();
					response.cmd = 2;
					connection.sendTCP(response);
				}
			}
		});
	}
}
