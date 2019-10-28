package net.mcorp.server.protocols.https;

import java.io.IOException;

import net.mcorp.server.Ticket;
import net.mcorp.server.protocols.HandlerThread;
import net.mcorp.server.protocols.https.ciphers.CipherSuites.CipherSuite;
import net.mcorp.server.protocols.https.extensions.Extensions;
import net.mcorp.server.protocols.https.records.handshakeOLD.Handshake;
import net.mcorp.server.protocols.https.records.handshakeOLD.HandshakeStub;
import net.mcorp.server.protocols.https.records.handshakeOLD.HandshakeType;
import net.mcorp.server.protocols.https.records.handshakeOLD.types.ClientHello;
import net.mcorp.server.protocols.https.records.handshakeOLD.types.ServerHello;

public class HttpsThread extends HandlerThread<Https, HttpsPacket> {

	protected HttpsThread(Ticket ticket) {
		super(Https.protocol, ticket);
	}

	@Override
	public void execute(HttpsPacket packet) {
		System.out.println("Starting up!!!");
		try {
			packet.readFromTicket();
			System.out.println(packet.toString());
			/*HandshakeStub stub = (HandshakeStub) packet.stub();
			if(stub == null)
				throw new IOException("CLOSE_ME");
			HandshakeType type = stub.typeObject();
			if(type == null)
				throw new IOException("CLOSE_ME");
			if(type instanceof ClientHello) {
				ClientHello cHello = (ClientHello) type;
				HandshakeStub response = Handshake.record.createNewStub();
				ServerHello sHello = new ServerHello(response);
				sHello.version(cHello.version());
				
				for(int id : cHello.ciphers().cipherIds()) {
					CipherSuite cipher = cHello.ciphers().getCipher(id);
					if(cipher != null) {
						sHello.cipher(cipher);
						break;
					}
				}
				
				sHello.compressionMethod(cHello.compressionMethods().getMethod(0));
				sHello.extensions(cHello.extensions());
				sHello.sessionId(cHello.sessionId());
				System.out.println(sHello.toString());
				//sHello.write(packet.ticket().socket.getOutputStream());
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof IOException) {
				if(e.getLocalizedMessage().equals("CLOSE_ME")) {
					try {
						packet.ticket().close();
					} catch(Exception e1) {}
					return;
				}
			}
		}
		while(true) {}
		
	}

}
