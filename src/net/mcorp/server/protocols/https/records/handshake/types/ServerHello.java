package net.mcorp.server.protocols.https.records.handshake.types;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class ServerHello extends HandshakeType{

	private int cipher = 0x00;
	public int cipher() { return this.cipher; };
	public void cipher(int cipher) { this.cipher = cipher; };
	
	@Override
	protected int length() {
		return 44;
	}

	@Override
	protected void readRoutine(InputStream in) throws IOException {
		
	}

	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		//Currently can only do... TLS_NULL_WITH_NULL_NULL
		
		String bin = "";
		bin += toBinStr(0x02,8);                      //Type
		bin += toBinStr(length(),3*8);                //Length
		bin += toBinStr(0x0303,2*8);                  //SSLVersion
		bin += toBinStr(new Random().nextLong(),8*8); //32 Byte Random
		bin += toBinStr(new Random().nextLong(),8*8); // /\
		bin += toBinStr(new Random().nextLong(),8*8); // /\
		bin += toBinStr(new Random().nextLong(),8*8); // /\
		bin += toBinStr(0x0,8);                       //SessionID Length
		bin += toBinStr(0x0,16);                      //Cipher
		bin += toBinStr(0x0,8);                       //Compression
		bin += toBinStr(0x00,16);                     //Extensions Length
		
		this.write(out, bin);
		
	}

	@Override
	public String toString(String indent, String indentBy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int type() {
		return 2;
	}

}
