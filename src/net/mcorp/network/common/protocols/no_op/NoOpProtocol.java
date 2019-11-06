package net.mcorp.network.common.protocols.no_op;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.protocols.Protocol;

/**
 * <h1>NoOpProtocol</h1>
 * <hr>
 * This class is used to define a NoOp {@linkplain Protocol}.
 * It will generate NoOp {@linkplain Packet}'s such as:<br>
 * <ul>
 * 	<li>{@linkplain NoOpReadPacket} - No Operational Read.</li>
 * 	<li>{@linkplain NoOpWritePacket} - No Operational Write.</li>
 * </ul>
 * <h2>What is NoOp?</h2>
 * <hr>
 * <p>
 * 	NoOp is short for "No Operation", which means that calling the {@linkplain #createNewReadPacket(Connection)} method, or the
 *  {@linkplain #createNewWritePacket(Connection)} will yield a new read or write {@linkplain Packet} (respectively) that when
 *  called to handle the connection will simply do nothing. 
 * </p>
 * @author Andrew Kerr
 */
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
