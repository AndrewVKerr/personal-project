package net.mcorp.network.common.protocols.old.no_op;

import java.io.InputStream;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.old.PacketConstructor;
import net.mcorp.network.common.protocols.old.Protocol;
import net.mcorp.network.common.protocols.old.ReadPacket;
import net.mcorp.network.common.protocols.old.TwoPacketConstructor;

/**
 * This class is used for testing purposes and/or for protocols that do not have a {@linkplain ReadPacket} at this time.
 * @author Andrew Kerr
 *
 */
public class NoOpReadPacket<Protocol_ extends Protocol<Protocol_,TwoPacketConstructor<Protocol_,NoOpReadPacket<Protocol_>,?>>> extends ReadPacket<Protocol_> {

	public NoOpReadPacket(Connection connection, Protocol_ protocol) {
		super(connection, protocol);
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"[NO OPERATION PACKET]";
	}

	@Override
	public void readCall(InputStream in) {
		return;
	}

}
