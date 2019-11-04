package net.mcorp.network.common.protocols;

import net.mcorp.network.common.Connection;

public abstract class Protocol<Protocol_ extends Protocol<Protocol_,ReadPacket_,WritePacket_>, ReadPacket_ extends ReadPacket<Protocol_>, WritePacket_ extends WritePacket<Protocol_>> {
	
	public abstract ReadPacket_ createNewReadPacket(Connection connection);
	public abstract WritePacket_ createNewWritePacket(Connection connection);
	
}
