package net.mcorp.network.common.protocols;

/**
 * This abstract class is used as a template to create a object that is responsible
 * for creating {@linkplain ReadPacket}'s and {@linkplain WritePacket}'s.
 * @apiNote It should be noted that each extending class should contain a private constructor and a static final instance object.
 * @author Andrew Kerr
 * @param <Protocol_> - {@linkplain Protocol} - The class that is extending this abstract class. (Used to properly setup read and write packets.)
 * @param <ReadPacket_> - {@linkplain ReadPacket} - The class of the reading packet.
 * @param <WritePacket_> - {@linkplain WritePacket} - The class of the writing packet.
 */
public abstract class Protocol<Protocol_ extends Protocol<Protocol_,PacketConstructor_>, PacketConstructor_ extends PacketConstructor<PacketConstructor_,Protocol_>> {
	
	public abstract PacketConstructor_ getConstructor(); 
	
}
