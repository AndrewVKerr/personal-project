package net.mcorp.server.protocols.https;

import java.io.InputStream;

import net.mcorp.server.protocols.AsymmetricalPacket;
import net.mcorp.server.protocols.https.records.RecordTypeStub;
import net.mcorp.server.protocols.https.records.RecordTypes;
import net.mcorp.server.protocols.https.records.handshake.Handshake;
import net.mcorp.server.protocols.https.records.handshake.HandshakeStub;
import net.mcorp.utils.BinaryUtilitys;

public class HttpsPacket extends AsymmetricalPacket implements BinaryUtilitys{

	protected HttpsPacket() { super(null); }
	private HttpsRecord record;
	public final HttpsRecord record() { return this.record; };
	
	private RecordTypeStub<?> stub;
	public final RecordTypeStub<?> stub() { return this.stub; };
	
	@Override
	protected void readInPacket(ConnectionMode type) throws Exception {
		InputStream in = this.ticket().socket.getInputStream();
		if(type == ConnectionMode.Server) {
			
			//Read in the record header
			int type_ = Integer.parseInt(getNextByte(in),2);
			int version_ = Integer.parseInt(getNextNBytes(in,2),2);
			int length_ = Integer.parseInt(getNextNBytes(in,2),2);
			record = new HttpsRecord(RecordTypes.getStaticRecordType(type_),version_,length_);
			
			//Check to make sure we are using TLSv1
			if(record.version() != 0x301)
				throw new Exception("Unsupported version ("+record.version()+")");
			
			//Begin handling
			if(record.type() == Handshake.record) {
				HandshakeStub hstub = Handshake.record.createNewStub(record);
				stub = hstub;
				hstub.read(in);
			}
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
		str += "\n"+indent+indentBy+"record="+(this.record != null ? this.record.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"stub="+(this.stub != null ? this.stub.toString(indent+indentBy, indentBy) : null)+",";
		str += "\n"+indent+indentBy+"";
		str += "\n"+indent+"]";
		return str;
	}

}
