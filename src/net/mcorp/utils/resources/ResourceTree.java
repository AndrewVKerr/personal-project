package net.mcorp.utils.resources;

import java.util.ArrayList;

public class ResourceTree {
	
	public final ArrayList<Resource> resources = new ArrayList<Resource>();
	
	public Resource notFound = null;
	
	public Resource search(String url) {
		String[] segs = url.split("/");
		int i = 0;
		while(segs[i].equals("")) { i++; };
		for(Resource resource : resources) {
			Resource res = resource.search(segs,i);
			if(res != null) {
				return res;
			}
		}
		return null;
	}
	
}
