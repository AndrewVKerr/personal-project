package net.mcorp.network.server.handlers;

import java.io.IOException;
import java.net.Socket;

import net.mcorp.network.common.exceptions.ConnectionException;
import net.mcorp.network.common.protocols.http.HttpRequest;
import net.mcorp.network.common.protocols.http.HttpResponse;
import net.mcorp.network.common.protocols.encryption.ssl.SSLConnection;
import net.mcorp.network.common.protocols.http.HttpException;
import net.mcorp.network.common.protocols.http.HttpProtocol;
import net.mcorp.network.server.ClientConnection;
import net.mcorp.network.server.ConnectionHandler;

public class HttpConnectionHandler extends ConnectionHandler{

	@Override
	public void handleAccept(Socket socket) {
		ClientConnection client = new ClientConnection(socket);
		ClientConnection ssl = client;
		//SSLConnection ssl = new SSLConnection(client);
		try {
			HttpRequest data = HttpProtocol.instance.read(ssl);
			if(data.url.get().equals("/")) {
				HttpResponse response = new HttpResponse();
				response.status_code.set(200);
				response.status_text.set("OK");
				response.httpVersion.set("http/1.1");
				response.headers.publish();
				response.data.set("Hello World!");
				HttpProtocol.instance.write(ssl, response);
			}else {
				HttpResponse response = new HttpResponse();
				response.status_code.set(404);
				response.status_text.set("Not Found");
				response.httpVersion.set("http/1.1");
				response.headers.publish();
				response.data.set("Not Found!");
				HttpProtocol.instance.write(ssl, response);
			}
		} catch (Exception e) {
			if(e instanceof HttpException) {
				HttpException he = (HttpException) e;
				try {
					HttpProtocol.instance.write(ssl, he.convertToHttpData());
				} catch (IOException e1) {}
			}
			e.printStackTrace();
		}finally {
			try {
				client.close();
			} catch (IOException e1) {}
		}
		
	}
	
}
