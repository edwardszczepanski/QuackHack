package Net;

import java.io.IOException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetClient {
	private Client client = new Client();

	public NetClient() {
		Kryo kryo = this.client.getKryo();
		kryo.register(Update.class);
		
		client.start();
		
		try {
			client.connect(5000, "10.111.250.117", 54555, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Receive Commands
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Update) {
					Update response = (Update)object;
					System.out.println(response.cmd);
				}
			}
		});
		
		Update response = new Update();
		response.cmd = 1;
		client.sendTCP(response);
	}
}
