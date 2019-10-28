package net.mcorp.server.protocols.https.records.alert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.server.protocols.https.records.RecordStub;

public class AlertStub extends RecordStub {

	@Override
	protected int length() {
		return 2;
	}

	@Override
	protected int recordType() {
		return 21;
	}

	@Override
	protected void readRoutine(InputStream in) throws IOException {
		
	}

	@Override
	protected void writeRoutine(OutputStream out) throws IOException {
		out.write(0x02);
		out.write(0x15);
	}

	@Override
	public String toString(String indent, String indentBy) {
		// TODO Auto-generated method stub
		return null;
	}

}
