package net.mcorp.networked;

import java.io.IOException;

import net.mcorp.home.devices.networked.NetworkedDevice;
import net.mcorp.networked.interfaces.server.Server;
import net.mcorp.networked.interfaces.server.handlers.TestServerHandler;

public class ServerTestMain {

	public static void main(String[] args) {
		NetworkedDevice.checkForEnableFile();
		try {
			Server<TestServerHandler> server = new Server<TestServerHandler>(new TestServerHandler(), 2000);
			server.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}