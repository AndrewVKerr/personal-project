package net.mcorp.network.common.protocols.old;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.utils.NetworkingUtils;

/**
 * This abstract class is used to define a basic structure for packets,
 * this class will contain a default constructor that will take in two
 * parameters and set them to {@linkplain Packet#connection} and 
 * {@linkplain Packet#protocol} respectively.
 * 
 * 
 * @author Andrew Kerr
 *
 * @param <Protocol_> - {@linkplain Protocol} - A protocol that this class object can reference as its creator.
 */
public abstract class Packet<Protocol_ extends Protocol<Protocol_,?>> extends NetworkingUtils{
	
	/**
	 * The Connection object this packet belongs to.
	 */
	protected final Connection connection;
	
	/**
	 * The Protocol object this packet belongs to.
	 */
	protected final Protocol_ protocol;
	
	public Packet(Connection connection, Protocol_ protocol) {
		this.connection = connection;
		this.protocol = protocol;
	}
	
}
