package net.mcorp.server.protocols;

import net.mcorp.network.common.exceptions.LockedValueException;

public abstract class AsymmetricalPacket extends Packet {

	protected AsymmetricalPacket(Packet dummy) {
		super(dummy);
	}

	/**
	 * The Mode that the server should communicate as.<hr>
	 * <h2>Different Modes</h2>
	 * <style>
	 * table,tr{
	 * 	border:1px #fff solid;
	 * 	padding:2px;
	 * }
	 * th,td{
	 * 	border:1px #fff solid;
	 * 	border-top:none;
	 * 	border-bottom:none;
	 * 	width:auto;
	 * 	text-align:center;
	 * 	padding:3px;
	 * }
	 * </style>
	 * <table>
	 * 	 <tr>
	 * 	   <th>Mode</th>
	 * 	   <th>Description</th>
	 * 	   <th>is Default?</th>
	 * 	 </tr>
	 * 	 <tr>
	 * 	   <td>Client</td>
	 * 	   <td>This server will act as though it is a client sending data to a server.</td>
	 * 	   <td><span style="color:rgb(255,0,0)">&#10007;</span></td>
	 * 	 </tr>
	 *   <tr>
	 * 	   <td>Server</td>
	 * 	   <td>This server will act as though it is a server sending data to a client.</td>
	 * 	   <td><span style="color:rgb(0,255,0)">&#10003;</span></td>
	 * 	 </tr>
	 * </table>
	 * @author Andrew Kerr
	 */
	public static enum ConnectionMode{
		/**
		 * When set to this mode this server should communicate as though it was talking to a server as a client.
		 */
		Client,
		
		/**
		 * When set to this mode this server should communicate as though it was talking to a client as a server.
		 */
		Server
	}
	
	/**
	 * The Mode that the server should communicate as.
	 */
	private ConnectionMode mode = ConnectionMode.Server;
	
	/**
	 * 
	 * @return
	 * @throws NullPointerException
	 */
	public ConnectionMode mode() throws NullPointerException{
		if(this.mode == null)
			throw new NullPointerException("[AsymetricalPacket.mode():NULL_VALUE_STORED] The value stored in the variable \"connectionType\" was set to null."); 
		return this.mode; 
	};
	
	public void mode(ConnectionMode type) throws LockedValueException, NullPointerException { 
		this.isLocked("AsymetricalPacket.mode(ConnectionMode)");
		if(type == null)
			throw new NullPointerException("[AsymetricalPacket.mode(ConnectionMode):NULL_VALUE_PARAM] The value passed as the parameter \"type\" was set to null.");
		this.mode = type;
	}
	
	/**
	 * This method will check to make sure that there is a valid ticket assigned to this object,
	 * and that the AsymmetricalPacket.isLocked() method returns false before calling another method
	 * AsymmetricalPacket.readInPacket() to read in all of the necessary data.
	 * <hr>
	 * <h2>Throwable Exceptions</h2>
	 * <style>
	 * table,tr{
	 * 	border:1px #fff solid;
	 * 	padding:2px;
	 * }
	 * th,td{
	 * 	border:1px #fff solid;
	 * 	border-top:none;
	 * 	border-bottom:none;
	 * 	width:auto;
	 * 	text-align:center;
	 * 	padding:3px;
	 * }
	 * td.left{ text-align: left; }
	 * </style>
	 * <table>
	 * 	 	<tr>
	 * 			<th>Exception</th>
	 * 			<th>Identifier</th>
	 * 			<th>Reason</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>RuntimeException</td>
	 * 			<td class="left"><b>INVALID_TICKET</b></td>
	 * 			<td class="left">
	 * 				You have not supplied the packet with a ticket to read from, without the ticket this method
	 * 				cannot do anything and would ultimately result in another {@linkplain Exception}.
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>RuntimeException</td>
	 * 			<td class="left"><b>ALREADY_LOCKED</b></td>
	 * 			<td class="left">
	 * 				This packet has been locked and cannot be edited any further, unfortunately the locking of
	 * 				a packet is permanent unless on very special occassions. Because of this lock this method
	 * 				cannot set any values as they are being read in.
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>NullPointerException</td>
	 * 			<td class="left"><b>NULL_VALUE_STORED</b></td>
	 * 			<td class="left">
	 * 				This exception will be thrown if there is a null pointer contained in the mode(ConnectionMode)
	 * 				variable.
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>Exception</td>
	 * 			<td class="left"><b>Anything remaining</b></td>
	 * 			<td class="left">
	 * 				Any other Exceptions will be thrown by the readInPacket method and have not been 
	 * 				documented as they are specific to the classes that inherit them.
	 * 			</td>
	 * 		</tr>
	 * </table>
	 * 
	 * @throws Exception Multiple Exceptions can be thrown for various reasons, see <b>Throwable Exceptions</b> section of documentation for more info.
	 */
	public final synchronized void readFromTicket() throws Exception {
		if(!this.validateTicket())
			throw new RuntimeException("[AsymetricalPacket.readFromTicket():INVALID_TICKET] Could not read in the next packet from ticket, missing ticket object within the packet object.");
		if(this.isLocked(null))
			throw new RuntimeException("[AsymetricalPacket.readFromTicket():ALREADY_LOCKED] Packet is already in a locked state, cannot interact with the packet due to this lock.");
		this.readInPacket(mode());
	}
	
	/**
	 * This method will check to make sure that there is a valid ticket assigned to this object,
	 * and that the AsymmetricalPacket.isLocked() method returns true before calling another method
	 * AsymmetricalPacket.writeToPacket() to write all of the necessary data to the ticket socket.<hr>
	 * <h2>Throwable Exceptions</h2>
	 * 
	 * <style>
	 * table,tr{
	 * 	border:1px #fff solid;
	 * 	padding:2px;
	 * }
	 * th,td{
	 * 	border:1px #fff solid;
	 * 	border-top:none;
	 * 	border-bottom:none;
	 * 	width:auto;
	 * 	text-align:center;
	 * 	padding:3px;
	 * }
	 * td.left{ text-align: left; }
	 * </style>
	 * <table>
	 * 	 	<tr>
	 * 			<th>Exception</th>
	 * 			<th>Identifier</th>
	 * 			<th>Reason</th>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>RuntimeException</td>
	 * 			<td class="left"><b>INVALID_TICKET</b></td>
	 * 			<td class="left">
	 * 				You have not supplied the packet with a ticket to read from, without the ticket this method
	 * 				cannot do anything and would ultimately result in another {@linkplain Exception}.
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>NullPointerException</td>
	 * 			<td class="left"><b>NULL_VALUE_STORED</b></td>
	 * 			<td class="left">
	 * 				This exception will be thrown if there is a null pointer contained in the mode(ConnectionMode)
	 * 				variable.
	 * 			</td>
	 * 		</tr>
	 * 		<tr>
	 * 			<td>Exception</td>
	 * 			<td class="left"><b>Anything remaining</b></td>
	 * 			<td class="left">
	 * 				Any other Exceptions will be thrown by the readInPacket method and have not been 
	 * 				documented as they are specific to the classes that inherit them.
	 * 			</td>
	 * 		</tr>
	 * </table>
	 * 
	 * @throws Exception Multiple Exceptions can be thrown for various reasons, see <b>Throwable Exceptions</b> section of documentation for more info.
	 */
	public final synchronized void writeToTicket() throws Exception {
		if(!this.validateTicket())
			throw new RuntimeException("[AsymetricalPacket.writeToTicket():INVALID_TICKET] Could not write the packet to the ticket, missing ticket object within the packet object.");
		if(!this.isLocked(null))
			throw new RuntimeException("[AsymetricalPacket.writeToTicket():MUST_LOCK_PACKET] Could not write the packet to the ticket, Packet must be in a locked state before sending.");
		this.writeToPacket(mode());
	}
	
	/**
	 * This abstract method is responsible for reading in the information from the packet.
	 * @param type - {@linkplain ConnectionMode} - The mode that the packet should read or write in. Cannot be null.
	 * @throws Exception Any Exceptions raised will properly be thrown.
	 */
	protected abstract void readInPacket(ConnectionMode type) throws Exception;
	
	/**
	 * This abstract method is responsible for writing out the information from the packet to the socket.
	 * @param type - {@linkplain ConnectionMode} - The mode that the packet should read or write in. Cannot be null.
	 * @throws Exception Any Exceptions raised will properly be thrown.
	 */
	protected abstract void writeToPacket(ConnectionMode type) throws Exception;
	
}
