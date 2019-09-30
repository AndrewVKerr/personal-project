package net.mcorp.utils.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.mcorp.server.Ticket;

public class Webpage extends Resource {

	public File file;
	
	public Webpage(String name, File file) {
		super(name);
		this.file = file;
		if(this.file == null || this.file.exists() == false) {
			System.err.println("Failed to load file: "+this.file+", make sure it exists.");
		}
	}

	@Override
	public void writeToTicket(Ticket ticket) throws IOException {
		if(this.file != null && this.file.exists() != false) {
			ticket.write("Http/1.1 200 OK\n\n");
			ticket.write(Files.readAllBytes(this.file.toPath()));
		}else {
			ticket.write("Http/1.1 204 No Content\n\n");
		}
	}

}
