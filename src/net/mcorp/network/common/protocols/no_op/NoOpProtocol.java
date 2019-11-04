package net.mcorp.network.common.protocols.no_op;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;

public class NoOpProtocol extends Protocol<NoOpProtocol,NoOpReadPacket<NoOpProtocol>,NoOpWritePacket<NoOpProtocol>>{

	public final static NoOpProtocol instance = new NoOpProtocol();
	
	private NoOpProtocol() {};
	
	@Override
	public NoOpReadPacket<NoOpProtocol> createNewReadPacket(Connection connection) {
		return new NoOpReadPacket<NoOpProtocol>(connection, this);
	}

	@Override
	public NoOpWritePacket<NoOpProtocol> createNewWritePacket(Connection connection) {
		return new NoOpWritePacket<NoOpProtocol>(connection, this);
	}

}
