package net.mcorp.network.common.protocols.encryption.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.encryption.EncryptedConnection;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.SSLHiddenLayer;

public class SSLConnection extends EncryptedConnection<SSLOutputStream, SSLInputStream>{
	
	public SSLConnection(Connection connection) {
		super(connection);
		this.hidden = new SSLHiddenLayer(this,connection);
	}
	
	public static enum SSLPhase{
		Waiting,
		Hello_Request,
		Client_Hello,
		Server_Hello,
		Certificate,
		Server_Key_Exchange,
		Certificate_Request,
		Server_Hello_Done,
		Client_Key_Exchange,
		Certificate_Verify,
		/**
		 * FINISHED
		 */
		Secured,
		Error
	}
	
	private SSLPhase phase = SSLPhase.Waiting;
	public SSLPhase phase() { return this.phase; }
	
	protected final SSLHiddenLayer hidden;
	private final SSLInputStream input = new SSLInputStream(this);
	private final SSLOutputStream output = new SSLOutputStream(this);
	
	@Override
	public InputStream getInputStream() throws ConnectionException {
		System.out.println("INPUTSTREAM");
		try {
			this.hidden.establishAndMaintainConnection();
		} catch (IOException e) {e.printStackTrace();}
		return input;
	}
	@Override
	public OutputStream getOutputStream() throws ConnectionException {
		try {
			this.hidden.establishAndMaintainConnection();
		} catch (IOException e) {e.printStackTrace();}
		return output;
	}
	
	private Exception exception;
	public Exception exception() { return this.exception; };
	
	public Exception error(Exception exception) {
		this.exception = exception;
		this.phase = SSLPhase.Error;
		return exception;
	};
	
	
	
}
