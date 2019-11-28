package net.mcorp.network.common.protocols.encryption.ssl.hidden;

import java.io.IOException;

import net.mcorp.common.utils.debug.SmartDebugInterface;
import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.encryption.EncryptHiddenLayer;
import net.mcorp.network.common.protocols.encryption.EncryptedConnection;
import net.mcorp.network.common.protocols.encryption.ssl.SSLConnection;
import net.mcorp.network.common.protocols.encryption.ssl.SSLConnection.SSLPhase;

/**
 * <h1>SSLHiddenLayer</h1>
 * <hr>
 * <p>
 * This class has one simple job. Create and maintain the ssl connection.
 * </p>
 * 
 * @author Andrew Kerr
 */
public class SSLHiddenLayer extends EncryptHiddenLayer implements Runnable{
	public final SSLConnection ssl;
	private final Thread maintainThread;
	
	public SSLHiddenLayer(SSLConnection ssl) {
		if(ssl.connection instanceof EncryptedConnection)
			throw new RuntimeException("[SSLHiddenLayer(SSLConnection,Connection):OVERRIDE_REQUIRED] Double encryption? Use Constructor SSLHiddenLayer(SSLConnection,Connection,Boolean) to override!");
		this.ssl = ssl;
		this.maintainThread = new Thread(this);
		this.maintainThread.setName("SSLHiddenLayerThread_"+this.hashCode());
		this.maintainThread.setDaemon(true);
		this.maintainThread.start();
	}
	
	public SSLHiddenLayer(SSLConnection ssl, boolean overrideDouble) {
		if(ssl.connection instanceof EncryptedConnection && overrideDouble == false)
			throw new RuntimeException("[SSLHiddenLayer(SSLConnection,Connection,Boolean):OVERRIDE_REQUIRED] Double encryption? overrideDouble parameter set to false! (Set true to override.)");
		this.ssl = ssl;
		this.maintainThread = new Thread(this);
		this.maintainThread.setName("SSLHiddenLayerThread_"+this.hashCode());
		this.maintainThread.setDaemon(true);
		this.maintainThread.start();
	}

	public synchronized void establishAndMaintainConnection() throws IOException{
		System.out.println("Checking phase! ("+ssl.phase()+")");
		while(ssl.phase() != SSLPhase.Secured) { //Check if connection is secured. If not attempt to secure the connection.
			
			//Check if connection is still open, if not bail.
			if(ssl.connection.isOpen() == false)
				throw (IOException) ssl.error(new IOException("[SSLHiddenLayer.establishAndMaintainConnection():SOCKET_CLOSED] Client closed the socket during the (re)securing of this connection."));
			
			//Check if an error has occurred, is so bail on local operations.
			if(ssl.phase() == SSLPhase.Error) {
				IOException ioe = new IOException("[SSLHiddenLayer.establishAndMaintainConnection():UNEXPECTED_EXCEPTION] The SSLConnection contains an error that has possibly disrupted the connection. SEE SUPPRESSED!");
				ioe.addSuppressed(ssl.exception());
				throw ioe;
			}
			
			//Attempt to establish a connection. If failed then set internal flags and bail.
			try {
				System.out.println("Attempting to establish connection!");
				SSLHiddenPacketData packet = new SSLHiddenPacketData(this);
				/*if(packet.handshake == null)
					throw new IOException("[SSLHiddenLayer.establishAndMaintainConnection():FAILED_STATE] Broken Pipe!");
				
				if(packet.handshake.data_type.get() == 1) {
					System.out.println("Client Hello!");
					SSLClientHello client_hello = (SSLClientHello) packet.handshake;
				}else {
					System.out.println("WRONG!");
				}*/
				System.out.println(packet.toString());
				
				System.out.println("DONE! (LOOP)");
				while(true) {}
				
			} catch (Exception e) {
				IOException ioe = (IOException)ssl.error(new IOException("[SSLHiddenLayer.establishAndMaintainConnection():HIDDEN_PACKET_DATA_ERROR] Unable to establish a proper connection. SEE SUPPRESSED!"));
				ioe.addSuppressed(e);
				throw ioe;
			}
			
		}
	}
	
	@Override
	public void write(int b) throws IOException {
		if(ssl.exception() != null) {
			if(ssl.exception() instanceof IOException)
				throw (IOException) ssl.exception();
			IOException ioe = new IOException("[SSLHiddenLayer.write(int):WRITE_FAILED] The SSL Connection has encountered an error that has prevented writing to the socket.");
			ioe.addSuppressed(ssl.exception());
			throw ioe;
		}
		
	}

	@Override
	public int read() throws IOException {
		if(ssl.exception() != null) {
			if(ssl.exception() instanceof IOException)
				throw (IOException) ssl.exception();
			IOException ioe = new IOException("[SSLHiddenLayer.read():READ_FAILED] The SSL Connection has encountered an error that has prevented reading from the socket.");
			ioe.addSuppressed(ssl.exception());
			throw ioe;
		}
		return 0;
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"ssl = "+this.ssl.toString()+""
				+ "\n"+indent+"]";
	}

	@Override
	public void run() {
		try {
			this.establishAndMaintainConnection();
		} catch (IOException e) {
			ssl.error(e);
		}
	}
	
	
}
