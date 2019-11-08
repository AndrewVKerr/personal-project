package net.mcorp.network.common.protocols.http;

import java.io.OutputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;
import net.mcorp.network.common.protocols.TwoPacketConstructor;
import net.mcorp.network.common.protocols.WritePacket;

public class HttpWPacket<Protocol_ extends Protocol<Protocol_,?>> extends WritePacket<Protocol_> {

	public HttpWPacket(Connection connection, Protocol_ protocol) {
		super(connection, protocol);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[]";
	}

	@Override
	public void writeCall(OutputStream in) {
		// TODO Auto-generated method stub
		
	}

}
