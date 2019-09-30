package net.mcorp.utils.resources;

import java.io.IOException;
import java.util.ArrayList;

import net.mcorp.server.Ticket;

public abstract class Resource {

	public final ArrayList<Resource> children = new ArrayList<Resource>();
	
	public String name;
	
	public Resource(String name) {
		this.name = name;
	}
	
	public Resource search(String[] segs, int i) {
		if(this.name.equals(segs[i])) {
			if(segs.length == i+1) return this;
			for(Resource r : children) {
				Resource res = r.search(segs, i+1);
				if(res != null) {
					return res;
				}
			}
			return null;
		}else {
			return null;
		}
	}

	public abstract void writeToTicket(Ticket ticket) throws IOException;
}
