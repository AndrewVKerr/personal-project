package net.mcorp.network.common.protocols.encryption.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import net.mcorp.network.common.Connection;
import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.encryption.EncryptedConnection;
import net.mcorp.network.common.protocols.encryption.ssl.hidden.SSLHiddenLayer;

/**
 * This class will apply the SSL Protocol over an existing {@linkplain Connection}.
 * When constructed this class will generate a {@linkplain SSLOutputStream} and a {@linkplain SSLInputStream}. Which when retrieved
 * via the {@linkplain #getOutputStream()} and the {@linkplain #getInputStream()} methods respectfully will be used for the next protocol.
 * This class also contains a {@linkplain SSLHiddenLayer}, this object will handle the conversion between ciphertext and plaintext. This
 * protocol with a few exceptions is non-recoverable, which means if an exception is thrown then the original connection must be terminated
 * so that it can be restarted if the client wishes. The state of the connection can be checked using the {@linkplain #phase()} method and
 * if the phase is set to {@linkplain SSLPhase#Error} then the thrown exception can be retrieved using the {@linkplain #exception()} method.
 * @apiNote This class extends the {@linkplain EncryptedConnection} class.
 * @author Andrew Kerr
 */
public class SSLConnection extends EncryptedConnection<SSLOutputStream, SSLInputStream>{
	
	public SSLConnection(Connection connection) {
		super(connection);
		this.hidden = new SSLHiddenLayer(this);
	}
	
	/**
	 * The state of the SSL Connection.
	 * @author Andrew Kerr
	 */
	public static enum SSLPhase{
		/**
		 * Connection has just been constructed and is waiting on the Client or Server to say hello.
		 */
		Waiting,
		
		/**
		 * This phase is sent via the server to the client, this informs the client that the server would like to restart the encrypting process.
		 */
		Hello_Request,
		
		/**
		 * 
		 */
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
	
	@Override
	public boolean isOpen() {
		return this.connection.isOpen();
	}
	
	@Override
	public void close() throws IOException {
		this.connection.close();
	};
	
	private Exception exception;
	public Exception exception() { return this.exception; };
	
	public Exception error(Exception exception) {
		this.exception = exception;
		this.phase = SSLPhase.Error;
		return exception;
	}
	
	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"connection = "+this.readSmartDebug(indent+indentBy, indentBy, this.connection)+","
				+ "\n"+indent+indentBy+"phase = SSLPhase["+this.phase.name()+"],"
				+ "\n"+indent+indentBy+"hidden = "+this.readSmartDebug(indent+indentBy, indentBy,this.hidden)+","
				+ "\n"+indent+indentBy+"input = "+this.input.toString()+","
				+ "\n"+indent+indentBy+"output = "+this.output.toString()+","
				+ "\n"+indent+indentBy+"exception = "+this.exception.getLocalizedMessage()+""
				+ "\n"+indent+"]";
	}
	
	
	
	
}
