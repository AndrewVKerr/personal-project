package net.mcorp.network.common;

import net.mcorp.network.common.protocols.ConnectionPacket;
import net.mcorp.network.common.protocols.PacketConstructor;
import net.mcorp.network.common.protocols.Protocol;

public class OnePacketConstructor<Protocol_ extends Protocol<Protocol_,OnePacketConstructor<Protocol_,CPacket_>>, CPacket_ extends ConnectionPacket<Protocol_>> extends PacketConstructor<OnePacketConstructor<Protocol_,CPacket_>, Protocol_> {

	public final Protocol_ protocol;
	public final Class<CPacket_> cpacket;
	
	public OnePacketConstructor(Protocol_ protocol, Class<CPacket_> cpacket) {
		this.protocol = protocol;
		this.cpacket = cpacket;
	}
	
}
