package net.mcorp.server.protocols.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;

import net.mcorp.server.protocols.AsymmetricalPacket;
import net.mcorp.server.protocols.http.statuscodes.HttpStatusCode;
import net.mcorp.server.protocols.http.statuscodes.HttpStatusCodes;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.exceptions.TimedOutException;

/**
 * 
 * @author Andrew Kerr
 */
public class HttpPacket extends AsymmetricalPacket{
	
	/**
	 * This {@linkplain HashMap} is used to store the values from the packet.
	 */
	private final HashMap<String,String> headerValues = new HashMap<String,String>();
	
	/**
	 * A Method is a subprotocol that is used by the HTTP protocol that determines
	 * how the server should handle the request.<hr>
	 * <h2>Supported Methods</h2>
	 * <p>This is a list of supported methods and some various data associated with them.<br><br>
	 * NOTE:<br>If "Use Caution?" is set to <span style="color:rgb(0,255,0)">&#10003;</span> that means that you should implement various checks
	 * to make sure that the client has the authority to perform that method.<br>
	 * If "Debug Only?" is set to <span style="color:rgb(0,255,0)">&#10003;</span> that means that the method should only be performed under debugging
	 * circumstances and should not be left in the finished product.<br>
	 * However these "If" statements will not be enforced as it is ultimately up to the programmers that implement theses methods to decide what to do about them.<br>
	 * </p>
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
	 * <tr><th>Method</th><th>Function</th><th>Use Caution?(Safe?)</th><th>Debug Only?</th></tr>
	 * <tr><td>GET</td><td>Retrieve the file or resource in its entirety.</td><td><span style="color:rgb(255,0,0)">&#10007;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>POST</td><td>Submit an entity to a file or resource for processing.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>HEAD</td><td>Retrieves the file or resource, however only send<br>the headers and not the payload.</td><td><span style="color:rgb(255,0,0)">&#10007;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>PUT</td><td>Put a entity at the specified location.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>PATCH</td><td>Patches an entity into a file or resource<br>at the specified location.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>DELETE</td><td>Deletes a file or resource at the specified location.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(0,255,0)">&#10003;</span></td></tr>
	 * <tr><td>OPTIONS</td><td>Client wishes to know what options are available<br>for communication.</td><td><span style="color:rgb(255,0,0)">&#10007;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * <tr><td>TRACE</td><td>Client wishes for the server to simply respond<br>with the clients request.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(0,255,0)">&#10003;</span></td></tr>
	 * <tr><td>CONNECT</td><td>Client wishes to open a tunnel with the server.</td><td><span style="color:rgb(0,255,0)">&#10003;</span></td><td><span style="color:rgb(255,0,0)">&#10007;</span></td></tr>
	 * </table>
	 * @since 9/13/2019
	 * @author Andrew Kerr
	 */
	public static enum Methods{
		/**
		 * The Get Method tells the server that the client wishes to retrieve the file or resource
		 * located at the provided url.
		 */
		GET,
		
		/**
		 * The Post Method tells the server that the client wishes to submit data to the file or resource
		 * located at the provided url.
		 */
		POST,
		
		/**
		 * The Head Method tells the server that the client wishes the same thing as the GET Method
		 * however this method requests that the payload or body not be submitted.
		 */
		HEAD,
		
		/**
		 * The Put Method tells the server that the client wishes to override the file or resource located
		 * at the provided url with the payload provided.
		 */
		PUT,
		
		/**
		 * The Patch Method tells the server that the client wishes to patch data into the file or resource
		 * located at the provided url.<br>
		 * NOTE: There should be an associated header that will indicate where the server should patch the data.
		 */
		PATCH,
		
		/**
		 * The Delete Method tells the server that the client wishes to delete the file or resource
		 * located at the provided url.<br>
		 * NOTE: This type of Method should not be taken lightly, you as the programmer must determine
		 * the best way to check if the resource could and should be deleted. You also need to check
		 * to make sure that the client has the authority to delete such a file or resource.
		 */
		DELETE,
		
		/**
		 * The Options Method tells the server that the client wishes to review the options for 
		 * Communicating with the server about a file or resource located at the provided url.
		 */
		OPTIONS,
		
		/**
		 * The Trace Method tells the server that the client wishes for the server to send the 
		 * request back.
		 */
		TRACE,
		
		/**
		 * The Connect Method tells the server that the client wishes to establish a tunnel using
		 * the provided url.
		 */
		CONNECT,
		
		/**
		 * This indicates that the method was not set, if this is the case it is quite possible that
		 * the ConnectionMode was set to Client.
		 */
		CLIENT_MODE_NONE
	}
	
	/**
	 * This is the the method the client wishes for the server to use when handling this request.
	 * Possible values are:
	 * 	GET, POST, HEAD, PUT, PATCH, DELETE, OPTIONS, TRACE, NONE
	 */
	private Methods Method = Methods.GET;
	
	public Methods Method() { return this.Method; };
	
	public void Method(Methods value) throws LockedValueException {
		this.isLocked("HttpPacket.Method(String)");
		this.Method = value;
	}
	
	public static long MAX_TIMEOUT = 1000;
	
	private String Url = "/";
	
	public String Url() { return this.Url; };
	
	public void Url(String value) throws LockedValueException {
		this.isLocked("HttpPacket.Url(String)");
		this.Url = value;
	}
	
	private String Version = "Http/1.1";
	
	public String Version() { return this.Version; };
	
	public void Version(String value) throws LockedValueException {
		this.isLocked("HttpPacket.Version(String)");
		this.Version = value;
	}
	
	private HttpStatusCode StatusCode = null;
	
	public HttpStatusCode StatusCode() { return this.StatusCode; };
	
	public void StatusCode(HttpStatusCode code) throws LockedValueException {
		this.isLocked("HttpPacket.StatusCode(Integer)");
		this.StatusCode = code;
	}
	
	private byte[] Payload = new byte[0];
	
	public byte[] Payload() { return this.Payload; };
	
	public void Payload(byte[] value) throws LockedValueException {
		this.isLocked("HttpPacket.Payload(byte[])");
		this.Payload = value;
	}
	
	public String getHeaderValue(String key) {
		return headerValues.get(key);
	}
	
	public void setHeaderValue(String key, String value) throws LockedValueException {
		this.isLocked("HttpPacket.setHeaderValue(String,String)");
		this.headerValues.put(key,value);
	}
	
	@Override
	protected void readInPacket(ConnectionMode mode) throws Exception {
		
		//Retrieve the BufferedReader from the Ticket provided, NOTE: Ticket has already been checked as to not be null.
		BufferedReader br = new BufferedReader(new InputStreamReader(this.ticket().socket.getInputStream()));
		
		/*
		 * Setup Timeout, since the BufferedReader will block while it is trying to read the next line
		 * we must check if the client is actually sending data as to prevent thread lockup. 
		 */
		long timeout = System.currentTimeMillis();
		while(!br.ready()) {
			//Timeout if client takes more than X milliseconds to respond.
			if(System.currentTimeMillis()-timeout >= MAX_TIMEOUT) {
				//Timeout, client is late to party and server isnt waiting for anyone.
				throw new TimedOutException("Socket failed to send data in time!");
			}
		}
		
		/**
		 * The stage determines what part of the packet we are reading in.
		 *    0) Method/Url/Version
		 *    1) Headers
		 *    2) Payload (if method indicates a payload.)
		 *    *) Break if anything else
		 */
		int stage = 0;
		
		String line;
		
		while(stage < 3 && (line = br.readLine()) != null) {
			if(stage == 0) { // Method/Url/Version or Version/Code/Status Text
				String[] s = line.split(" ", 3);
				if(s.length != 3)
					throw new IOException("Failed to read the first line! "+line);
				if(mode.equals(ConnectionMode.Server)) {
					Method = Methods.valueOf(s[0]);
					Url = s[1];
					Version = s[2];
				}else
				if(mode.equals(ConnectionMode.Client)) {
					Method = Methods.CLIENT_MODE_NONE;
					Version = s[0];
					StatusCode = HttpStatusCodes.instance.getCode(Integer.parseInt(s[1]));
				}else {
					throw new IOException("Unsupported ConnectionMode!!!");
				}
				stage = 1; //Move to next stage
			}else
			if(stage == 1) {
				if(line.equals("")) { //Check to see if this is the double newline, if so then we are done with headers.
					boolean isMore = true;
					//Use timeout to check if client is sending payload data, if so then retrieve it else break;
					timeout = System.currentTimeMillis();
					while(!br.ready()) { if(System.currentTimeMillis()-timeout >= MAX_TIMEOUT) {isMore = false;break;} };
					if(isMore) {
						stage = 2;
					}else {
						stage = 3; break;
					}
				}else {
					if(!line.contains(": "))
						throw new IOException("Malformed Header: Missing ': '; "+line);
					String[] s = line.split(": ",2);
					if(s.length != 2)
						throw new IOException("Malformed Header: "+line);
					if(s[0].startsWith("_"))
						throw new IOException("A Connection attempted to send over a reserved header name! "+line);
					headerValues.put(s[0], s[1]);
				}
			}else
			if(stage == 2) {
				byte[] b = line.getBytes();
				byte[] o = this.Payload;
				byte[] n = new byte[b.length+o.length];
				int i = 0;
				for(byte x : o) {
					n[i] = x;
					i++;
				}
				for(byte x : b) {
					n[i] = x;
					i++;
				}
			}else {
				break;
			}
			timeout = System.currentTimeMillis();
			while(stage > 3 && !br.ready()) {
				if(System.currentTimeMillis()-timeout >= MAX_TIMEOUT) {
					stage = 3;
					break;
				}
			}
		}
		this.lock();
	}

	@Override
	protected void writeToPacket(ConnectionMode mode) throws Exception {
		OutputStream out = this.ticket().socket.getOutputStream();
		
		if(mode.equals(ConnectionMode.Server)) {
			out.write((Version+" "+StatusCode.getCode()+" "+StatusCode.getText()+"\n").getBytes());
			for(String key : this.headerValues.keySet()) {
				out.write((key+": "+this.headerValues.get(key)+"\n").getBytes());
			}
			out.write("\n".getBytes());
			out.write(Payload);
			out.flush();
		}else
		if(mode.equals(ConnectionMode.Client)) {
			out.write((Method+" "+Url+" "+Version+"\n").getBytes());
			for(String key : this.headerValues.keySet()) {
				out.write((key+": "+this.headerValues.get(key)+"\n").getBytes());
			}
			out.write("\n".getBytes());
			out.write(Payload);
			out.flush();
		}else {
			throw new Exception("Unsupported ConnectionMode!");
		}
	}

	protected HttpPacket() { super(null); };
	
}
