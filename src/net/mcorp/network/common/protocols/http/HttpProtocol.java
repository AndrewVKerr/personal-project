package net.mcorp.network.common.protocols.http;

import java.io.InputStream;
import java.io.OutputStream;

import net.mcorp.network.common.protocols.Protocol;

public class HttpProtocol extends Protocol<HttpData, HttpData> {

	@Override
	protected HttpData readCall(InputStream in) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeCall(OutputStream out, HttpData data) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
