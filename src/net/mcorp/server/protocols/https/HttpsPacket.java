package net.mcorp.server.protocols.https;

import java.io.InputStream;

import net.mcorp.server.protocols.AsymmetricalPacket;
import net.mcorp.server.protocols.https.records.Record;
import net.mcorp.server.protocols.https.records.RecordStub;
import net.mcorp.server.protocols.https.records.alert.AlertStub;
import net.mcorp.server.protocols.https.records.alert.HttpsAlert;
import net.mcorp.server.protocols.https.records.handshake.Handshake;
import net.mcorp.server.protocols.https.records.handshake.HandshakeStub;
import net.mcorp.server.protocols.https.records.handshake.types.ClientHello;
import net.mcorp.server.protocols.https.records.handshake.types.ServerHello;
import net.mcorp.utils.BinaryUtilitys;

public class HttpsPacket extends AsymmetricalPacket implements BinaryUtilitys{

	protected HttpsPacket() { super(null); }
	private Record record;
	public final Record record() { return this.record; };
	
	@Override
	protected void readInPacket(ConnectionMode type) throws Exception {
		InputStream in = this.ticket().socket.getInputStream();
		if(type == ConnectionMode.Server) {
			
			//Read in the record header
			int type_ = Integer.parseInt(getNextByte(in),2);
			int version_ = Integer.parseInt(getNextNBytes(in,2),2);
			int length_ = Integer.parseInt(getNextNBytes(in,2),2);
			record = Record.generateListeningRecord(type_, version_, length_);
			
			//Check to make sure we are using TLSv1
			if(record.version() != 0x301)
				throw new Exception("Unsupported version ("+record.version()+")");
			
			if(record.recordType() == 0x16) { // Handshake
				HandshakeStub stub = Handshake.instance.createListenerStub();
				record.stub(stub);
				stub.read(this.ticket().socket.getInputStream());
				
				HandshakeStub resp = Handshake.instance.createResponseStub();
				resp.writeOnly();
				
				ServerHello hello = new ServerHello();
				hello.writeOnly();
				hello.cipher(((ClientHello) (stub.handshakeType())).cipher());
				
				resp.handshakeType(hello);
				
				//AlertStub alert = HttpsAlert.instance.createResponseStub();
				//alert.writeOnly();
				
				Record response = Record.generateWritingRecord(record.version(), resp);
				
				response.write(this.ticket().socket.getOutputStream());
			}
			
			/*record = new HttpsRecord(RecordTypes.getStaticRecordType(type_),version_,length_);
			//Begin handling
			if(record.type() == Handshake.record) {
				HandshakeStub hstub = Handshake.record.createNewStub(record);
				stub = hstub;
				hstub.read(in);
			}*/
		}else {
			System.err.println("Client connection not supported! Https");
		}
	}

	@Override
	protected void writeToPacket(ConnectionMode type) throws Exception {
		
	}
	
	public String toString() {
		return this.toString("","\t");
	}
	
	public String toString(String indent, String indentBy) {
		String str = this.getClass().getSimpleName()+"[";
		str += "\n"+indent+indentBy+"record="+(this.record != null ? this.record.toString(indent+indentBy, indentBy) : null)+"";
		str += "\n"+indent+"]";
		return str;
	}

}
