package net.mcorp.spector.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <h1>Connection</h1>
 * <hr>
 * <p>
 * 	This abstract class defines what a connection (node) should look like. When this class is extended
 * 	it should be used as a means of either gaining data or sending data to and from a client or as a means
 * 	of taking that data and either encrypting or decrypting it.
 * </p>
 * <hr>
 * @author Andrew Kerr
 *
 * @param <Reader> - {@linkplain InputStream} - An inputstream used to gain data from the connection.
 * @param <Writer> - {@linkplain OutputStream} - An outputstream used to send data to the connection.
 */
public abstract class Connection<Reader extends InputStream, Writer extends OutputStream> {
	
	/**
	 * Obtains the reader from this connection.
	 * @return {@linkplain #Reader} - The reader from this connection.
	 * @implNote This method should not be called from any outside class, it is simply used to communicate 
	 * between the raw binary into real information that the server can use. This process is a chain and as such
	 * only one link should be connected to another. For this reason this method will check if the calling class is
	 * an extension of {@linkplain Connection}.
	 */
	public final Reader getReader() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		if(Connection.class.isAssignableFrom(ste[1].getClass())) {
			try {
				return this.reader();
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * This method should not be called directly as it is meant to be called by the {@linkplain #getReader()} method.
	 * @return {@linkplain #Reader} - Returns this classes reader.
	 * @throws IOException Thrown if any IO errors occurred during the retrieval process.
	 */
	protected abstract Reader reader() throws IOException;
	
	/**
	 * Obtains the writer from this connection.
	 * @return {@linkplain #Writer} - The writer for this connection.
	 * @implNote This method should not be called from any outside class, it is simply used to communicate 
	 * between the raw binary into real information that the server can use. This process is a chain and as such
	 * only one link should be connected to another. For this reason this method will check if the calling class is
	 * an extension of {@linkplain Connection}.
	 */
	public final Writer getWriter() {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		if(Connection.class.isAssignableFrom(ste[1].getClass())) {
			try {
				return this.writer();
			} catch (IOException e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * This method should not be called directly as it is meant to be called by the {@linkplain #getWriter()} method.
	 * @return {@linkplain #Writer} - Returns this classes writer. 
	 * @throws IOException Thrown if any IO errors occurred during the retrieval process.
	 */
	protected abstract Writer writer() throws IOException;
	
}
