package net.mcorp.network.common.protocols;

import net.mcorp.network.common.Connection;

public class ConnectionPacket<Protocol_ extends Protocol<Protocol_,?>> extends Packet<Protocol_> {

	public ConnectionPacket(Connection connection, Protocol_ protocol) {
		super(connection, protocol);
	}

	@Override
	public String toString(String indent, String indentBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
