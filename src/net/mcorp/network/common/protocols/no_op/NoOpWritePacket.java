package net.mcorp.network.common.protocols.no_op;

import java.io.OutputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;
import net.mcorp.network.common.protocols.TwoPacketConstructor;
import net.mcorp.network.common.protocols.WritePacket;

public class NoOpWritePacket<Protocol_ extends Protocol<Protocol_,?>> extends WritePacket<Protocol_> {

	public NoOpWritePacket(Connection connection, Protocol_ protocol) {
		super(connection,protocol);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[No Operation Packet]";
	}

	@Override
	public void writeCall(OutputStream in) {
		return;
	}

	
}