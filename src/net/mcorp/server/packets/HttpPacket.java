package net.mcorp.server.packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import net.mcorp.server.Packet;
import net.mcorp.server.Ticket;
import net.mcorp.utils.exceptions.TimedOutException;

@Deprecated
public class HttpPacket extends Packet {
	
	public HttpPacket(Ticket ticket) {
		super(ticket);
	}

	public static final String URL = "_url";
	public static final String METHOD = "_method";
	public static final String VERSION = "_version";
	
	private HashMap<String,String> values = new HashMap<String,String>();
	public String getValue(String key) { return values.get(key); };
	
	private String data = "";
	public String data() { return this.data; };
	
	@Override
	public void readInPacket() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(ticket().socket.getInputStream()));
		long timeout = System.currentTimeMillis();
		while(!br.ready()) {
			if(System.currentTimeMillis()-timeout >= 500) {
				throw new TimedOutException("Socket failed to send data in time!");
			}
		}
		int stage = 0;
		String line;
		while(stage < 3 && (line = br.readLine()) != null) {
			if(stage == 0) {
				String[] s = line.split(" ", 3);
				if(s.length != 3)
					throw new IOException("Failed to read the first line! "+line);
				values.put(METHOD,s[0]);
				values.put(URL, s[1]);
				values.put(VERSION, s[2]);
				stage = 1;
			}else
			if(stage == 1) {
				if(line.equals("")) {
					if(values.get(METHOD).equals("POST")) {
						stage = 2;
					}else {
						stage = 3;	
					}
				}else {
					if(!line.contains(": "))
						throw new IOException("Malformed Header: Missing ': '; "+line);
					String[] s = line.split(": ",2);
					if(s.length != 2)
						throw new IOException("Malformed Header: "+line);
					if(s[0].startsWith("_"))
						throw new IOException("A Connection attempted to send over a reserved header name! "+line);
					values.put(s[0], s[1]);
				}
			}else
			if(stage == 2) {
				if(values.get(METHOD).equals("POST")) {
					data += line+"\n";
				}else {
					stage = 3;
				}
			}else {
				break;
			}
			timeout = System.currentTimeMillis();
			while(!br.ready()) {
				if(System.currentTimeMillis()-timeout >= 500) {
					stage = 3;
					break;
				}
			}
		}
		this.finishReading();
	}

}
