package net.mcorp.server.protocols.https.records;

import java.util.ArrayList;

import net.mcorp.server.protocols.https.records.handshakeOLD.Handshake;

public final class RecordTypes {
	
	public static final RecordTypes instance = new RecordTypes();
	
	private RecordTypes() { //Prevent any other instances of this class from being generated.
		this.addRecord(Handshake.record);
	}
	
	private ArrayList<RecordType<?>> records = new ArrayList<RecordType<?>>();
	
	public void addRecord(RecordType<?> record) {
		if(this.getRecordType(record.getValue()) == null) {
			this.records.add(record);
		}
	}
	
	public RecordType<?> getRecordType(int value) {
		for(RecordType<?> t : this.records) {
			if(t.getValue() == value)
				return t;
		}
		return null;
	}
	
	public static RecordType<?> getStaticRecordType(int value) {
		return instance.getRecordType(value);
	}
	
}
