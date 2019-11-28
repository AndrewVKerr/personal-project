package net.mcorp.network.common.protocols.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.TimeoutException;

import net.mcorp.common.sync.SynchronizedVariable;
import net.mcorp.network.common.protocols.Protocol;
import net.mcorp.network.common.protocols.http.HttpHeaders.HttpHeader;
import net.mcorp.network.common.protocols.http.HttpRequest.Method;

public class HttpProtocol extends Protocol<HttpRequest, HttpResponse> {

	public static final HttpProtocol instance = new HttpProtocol();
	public final SynchronizedVariable<Long> timeout = new SynchronizedVariable<Long>(0L);
	
	protected HttpProtocol() {
		
	};
	
	@Override
	protected HttpRequest readCall(InputStream in) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		HttpRequest data = null;
		try {
			data = new HttpRequest();
			String line = "";
			
			//===] Check if client is ready to talk.
			long block_timer = System.currentTimeMillis();
			while(timeout.get() > 0 && !br.ready()) {
				if(System.currentTimeMillis()-block_timer > timeout.get())
					throw new TimeoutException("[HttpProtocol.readCall(InputStream):TIMEOUT] Client failed to send more data before timeout occurred.");
			}
			
			//===] Client should be ready at this state, attempting to read in next packet....
			line = br.readLine();
			String[] packet_head = line.split(" ",3);
			if(packet_head.length != 3)
				throw new HttpException(400,"Broken packet head");
			
			data.method.set(HttpRequest.Method.valueOf(packet_head[0]));
			data.url.set(packet_head[1]);
			data.version.set(packet_head[2]);
			
			//===] Read in headers
			while(line != "" && !line.equals("")) {
				//===] Check if client is ready to talk.
				block_timer = System.currentTimeMillis();
				while(timeout.get() > 0 && !br.ready()) {
					if(System.currentTimeMillis()-block_timer > timeout.get())
						throw new TimeoutException("[HttpProtocol.readCall(InputStream):TIMEOUT] Client failed to send more data before timeout occurred.");
				}
				
				line = br.readLine();
				if(line.contains(":")) {
					String[] header = line.split(":",2);
					if(header.length != 2)
						throw new HttpException(400,"Broken packet header pair");
					HttpHeader httpHeader = new HttpHeader();
					httpHeader.name.set(header[0].strip().toLowerCase());
					httpHeader.value.set(header[1].strip());
					data.headers.addHeader(httpHeader);
				}
					
			}
			
			data.headers.publish();
			
			if(data.method.get() != Method.GET) {
				//Check if timeout is set, if it isn't then we must insist that each packet is accompanied by a length header.
				if(timeout.get() > 0 && data.headers.getHeader("content-length") != null) {
					while(true) {
						//===] Check if client is ready to talk.
						block_timer = System.currentTimeMillis();
						while(timeout.get() > 0 && !br.ready()) {
							if(System.currentTimeMillis()-block_timer > timeout.get())
								throw new TimeoutException("[HttpProtocol.readCall(InputStream):TIMEOUT] Client failed to send more data before timeout occurred.");
						}
					}
				}else {
					throw new HttpException(411,"Length Required");
				}
			}
			
		}catch(Exception e) {
			if(e instanceof TimeoutException) {
				data.timeoutException.set((TimeoutException)e);
			}else {
				throw e;
			}
		}
		return data;
	}

	@Override
	protected void writeCall(OutputStream out, HttpResponse data) throws Exception {
		out.write((data.httpVersion.get()+" "+data.status_code.get()+" "+data.status_text.get()+"\n").getBytes());
		Object[] headers = data.headers.getHeaders().toArray();
		if(headers.length != 0) {
			for(Object obj : headers) {
				if(obj instanceof HttpHeader) {
					HttpHeader header = (HttpHeader) obj;
					out.write((header.name.get()+": "+header.value.get()+"\n").getBytes());
				}
			}
			out.write("\n".getBytes());
		}else {
			out.write("\n\n".getBytes());
		}
		out.write(data.data.get().getBytes());
	}

}
