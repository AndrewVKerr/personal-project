package net.mcorp.server.protocols.https.records;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import net.mcorp.utils.BinaryUtils;

public abstract class RecordStub extends BinaryUtils {

	/**
	 * The length of the packet as it currently stands in number of binary bytes.<hr>
	 * NOTE: This number should be the accumulation of all of the pieces that are contained in this object.
	 * For best results each object should have a length method that will ask the next object what its length is whilst adding its own length on.<br>
	 * Example:<br>
	 * <pre>
	 * int length(){
	 * 	return 5 +		//Our length gets added on first, then...
	 * 		test.length();	//An arbitrary example object's length gets added on next. (We let this length method worry about any further calculations.)
	 * }
	 * </pre>
	 * 
	 * @return {@linkplain Integer} - The accumulation of all the length values contained within this object.
	 */
	protected abstract int length();
	
	/**
	 * The record type is a identifier that quickly determines what type of packet is being sent through.
	 * @return {@linkplain Integer} - The kind of record this stub should represent.
	 */
	protected abstract int recordType();

	/**
	 * 
	 */
	private boolean writeOnly = false;
	public void writeOnly() { this.writeOnly = true; };
	public boolean isWriteOnly() { return this.writeOnly; };
	
	private boolean readFinished = false;
	
	/**
	 * Attempts to read in values from the provided {@linkplain InputStream}(in).<br>
	 * <p>
	 * 		<b>Note:</b><br>
	 * 			It should be noted that if this method throws an exception then the server has been desynced from the client. If this happens
	 * 			then the server should immediately close the connection as it will be very rare to resync with the client. If the connection did
	 * 			get far enough and if the connection has the ability to, there might be a way to resync using an extension or cipher: If there is no
	 * 			way to resync then please close the connection so that the client can attempt to connect again.
	 * </p>
	 * @param in - {@linkplain InputStream} - The stream to read from.
	 * @throws IOException Thrown if RecordStub.readRoutine(InputStream) throws an exception or if writeOnly is true or if this method has already been called once before.
	 */
	public void read(InputStream in) throws IOException{
		if(this.writeOnly)
			throw new IOException("[RecordStub.read():WRITE_ONLY] Cannot read from the socket as this stub has been set to write only.");
		if(this.readFinished)
			throw new IOException("[RecordStub.read():READ_DONE] This method has already been called on this object before, and can only be called once.");
		this.readRoutine(in);
		this.readFinished = true;
	}
	
	/**
	 * This method gets called whenever this stub is generated as a listening object.
	 * @param in - {@linkplain InputStream} - The stream to listen from.
	 * @throws IOException Thrown if InputStream encounters a problem.
	 */
	protected abstract void readRoutine(InputStream in) throws IOException;
	
	/**
	 * Attempts to write the values to the provided {@linkplain OutputStream}(out).<br>
	 * <p>
	 * 		<b>Note:</b><br>
	 * 			It should be noted that if this method throws an exception then the server has been desynced from the client. If this happens
	 * 			then the server should immediately close the connection as it will be very rare to resync with the client. If the connection did
	 * 			get far enough and if the connection has the ability to, there might be a way to resync using an extension or cipher: If there is no
	 * 			way to resync then please close the connection so that the client can attempt to connect again.
	 * </p>
	 * @param out - {@linkplain OutputStream} - The stream to write to.
	 * @throws IOException Thrown if RecordStub.writeRoutine(OutputStream) throws an exception or if writeOnly is false.
	 */
	public void write(OutputStream out) throws IOException{
		if(!this.writeOnly)
			throw new IOException("[RecordStub.read():READ_ONLY] Cannot write to the socket as this stub has been set to read only.");
		this.writeRoutine(out);
	}
	
	/**
	 * This method gets called whenever this stub is generated as a response object.
	 * @param out - {@linkplain OutputStream} - The stream to dump information into.
	 * @throws IOException Thrown if OutputStream encounters a problem.
	 */
	protected abstract void writeRoutine(OutputStream out) throws IOException;
	
}
