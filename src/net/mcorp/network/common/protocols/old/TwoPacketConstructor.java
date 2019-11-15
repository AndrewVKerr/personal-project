package net.mcorp.network.common.protocols.old;

import java.lang.reflect.Constructor;

import net.mcorp.network.common.Connection;

public class TwoPacketConstructor<Protocol_ extends Protocol<Protocol_,TwoPacketConstructor<Protocol_>>> extends PacketConstructor<TwoPacketConstructor<Protocol_>, Protocol_> {

	public final Protocol_ protocol;
	public final Class<? extends ReadPacket<Protocol_>> rPacketClass;
	public final Class<? extends WritePacket<Protocol_>> wPacketClass;
	
	public TwoPacketConstructor(Protocol_ protocol, Class<? extends ReadPacket<Protocol_>> rpacket, Class<? extends WritePacket<Protocol_>> wpacket) {
		if(protocol == null)
			throw new NullPointerException("Protocol must not be null!");
		this.protocol = protocol;
		this.rPacketClass = rpacket;
		this.wPacketClass = wpacket;
	}
	
	public ReadPacket<Protocol_> createNewReadPacket(Connection connection) {
		try {
			Constructor<? extends ReadPacket<Protocol_>> constructor = this.rPacketClass.getConstructor(Connection.class, this.protocol.getClass());
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
	public WritePacket<Protocol_> createNewWritePacket(Connection connection) {
		try {
			Constructor<? extends WritePacket<Protocol_>> constructor = this.wPacketClass.getConstructor(Connection.class, this.protocol.getClass());
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
