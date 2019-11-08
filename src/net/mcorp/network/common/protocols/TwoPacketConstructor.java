package net.mcorp.network.common.protocols;

import java.lang.reflect.Constructor;

import net.mcorp.network.common.Connection;

public class TwoPacketConstructor<Protocol_ extends Protocol<Protocol_,TwoPacketConstructor<Protocol_,RPacket_,WPacket_>>, RPacket_ extends ReadPacket<Protocol_>, WPacket_ extends WritePacket<Protocol_>> extends PacketConstructor<TwoPacketConstructor<Protocol_,RPacket_,WPacket_>, Protocol_> {

	public final Protocol_ protocol;
	public final Class<RPacket_> rPacketClass;
	public final Class<WPacket_> wPacketClass;
	
	public TwoPacketConstructor(Protocol_ protocol, Class<RPacket_> rpacket, Class<WPacket_> wpacket) {
		if(protocol == null)
			throw new NullPointerException("Protocol must not be null!");
		this.protocol = protocol;
		this.rPacketClass = rpacket;
		this.wPacketClass = wpacket;
	}
	
	public RPacket_ createNewReadPacket(Connection connection) {
		try {
			Constructor<RPacket_> constructor = this.rPacketClass.getConstructor(Connection.class, this.protocol.getClass());
			if(constructor != null && constructor.canAccess(this)) {
				return constructor.newInstance(connection,this.protocol);
			}
		}catch(Exception e) {
			RuntimeException re = new RuntimeException("Unable to create a new read packet!");
			re.addSuppressed(e);
			throw re;
		}
		return null;
	}
	public WPacket_ createNewWritePacket(Connection connection) {
		try {
			Constructor<WPacket_> constructor = this.wPacketClass.getConstructor(Connection.class, this.protocol.getClass());
			if(constructor != null && constructor.canAccess(this)) {
				return constructor.newInstance(connection,this.protocol);
			}
		}catch(Exception e) {
			RuntimeException re = new RuntimeException("Unable to create a new read packet!");
			re.addSuppressed(e);
			throw re;
		}
		return null;
	}
	
}
