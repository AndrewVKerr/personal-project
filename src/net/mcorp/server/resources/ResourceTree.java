package net.mcorp.server.resources;

import java.util.ArrayList;

import net.mcorp.server.resources.transferable.TransferableObject;
import net.mcorp.server.resources.transferable.Webpage;
import net.mcorp.utils.exceptions.LockedValueException;
import net.mcorp.utils.lockable.LockableObject;

public final class ResourceTree extends LockableObject{
	
	public static final class ResourceUrl{
		
		private ResourceTree tree;
		
		public ResourceTree tree() { return this.tree; };
		
		private String urlSegment;
		
		public synchronized void urlSegment(String urlSegment) throws LockedValueException {
			if(tree.isLocked()) {
				throw new LockedValueException("[ResourceUrl.urlSegment(String):INTERNAL_TREE_LOCKED] The tree object that owns this ResourceUrl is currently locked and needs to be unlocked before any changes to it or it's children can be done.");
			}
			this.urlSegment = urlSegment; 
		};
		
		public synchronized String urlSegment() { return this.urlSegment; };
		
		public synchronized String url() {
			return (this.parent == null || (this.tree == null ? false : this.parent == this.tree.root) ? "/"+this.urlSegment : this.parent.url()+"/"+this.urlSegment);
		}
		
		private ResourceUrl parent;
		
		public synchronized ResourceUrl parent() { return this.parent; };
		
		private ArrayList<ResourceUrl> children = new ArrayList<ResourceUrl>();
		
		public synchronized ResourceUrl[] getChildren() {
			return this.children.toArray(new ResourceUrl[] {});
		}
		
		public synchronized ResourceUrl getNextUrl(String urlSegment) {
			for(ResourceUrl child : children) {
				if(child.urlSegment.equals(urlSegment)) {
					return child;
				}
			}
			return null;
		}
		
		public synchronized ResourceUrl createChild(String urlSegment, TransferableObject transferableObj) throws LockedValueException {
			ResourceUrl child = new ResourceUrl(this.tree);
			this.addUrl(child);
			child.urlSegment(urlSegment);
			child.resource(transferableObj);
			child.parent = this;
			return child;
		};
		
		protected synchronized void addUrl(ResourceUrl child) throws LockedValueException {
			if(tree.isLocked()) {
				throw new LockedValueException("[ResourceUrl.addUrl(ResourceUrl):INTERNAL_TREE_LOCKED] The tree object that owns this ResourceUrl is currently locked and needs to be unlocked before any changes to it or it's children can be done.");
			}
			this.children.add(child); 
		};	
		
		private TransferableObject resource;
		
		public synchronized TransferableObject resource() { return this.resource; };
		
		public synchronized void resource(TransferableObject resource) throws LockedValueException {
			if(tree.isLocked()) {
				throw new LockedValueException("[ResourceUrl.resource(TransferableObject):INTERNAL_TREE_LOCKED] The tree object that owns this ResourceUrl is currently locked and needs to be unlocked before any changes to it or it's children can be done.");
			}
			if(this.resource != null)
				this.resource.url(this);
			this.resource = resource;
			if(this.resource != null)
				this.resource.url(this);
		};
		
		protected ResourceUrl(ResourceTree tree) { this.tree = tree; }
		
		public synchronized String toString() {
			return this.toString("", "   ");
		}
		
		public synchronized String toString(String indent, String indentBy) {
			String str = "ResourceUrl[";
			indent += indentBy;
			str += "\n"+indent+"tree = "+(tree == null ? "Null_Value" : "ResourceTree[...]")+",";
			str += "\n"+indent+"resource = "+(resource == null ? "Null_Value" : resource.toString(indent+indentBy,indentBy))+",";
			str += "\n"+indent+"urlSegment = "+(urlSegment == null ? "Null_Value" : "String[\""+urlSegment+"\"]")+",";
			str += "\n"+indent+"parent = "+(parent == null ? "Null_Value" : "ResourceUrl[...]")+",";
			str += "\n"+indent+"children = [";
			for(ResourceUrl child : children) {
				if(children.indexOf(child) != 0)
					str+=",";
				str+="\n"+indent+indentBy+(child == null ? "null" : child.toString(indent+indentBy,indentBy));
			}
			str += (children.size() > 0 ? "\n"+indent : "")+"]";
			str+="\n"+indent.substring(0,(indent.length() < indentBy.length() ? indent.length() : indent.length()-indentBy.length()))+"]";
			return str;
		}
		
	}
	
	private ResourceUrl root = new ResourceUrl(this);
	public ResourceUrl root() { return this.root; };
	
	private ResourceUrl landing = null;
	public ResourceUrl landing() { return this.landing; };
	public void landing(ResourceUrl resourceUrl) throws LockedValueException { this.isLocked("ResourceTree.landing(ResourceUrl)"); this.landing = resourceUrl; }; 

	public ResourceTree() {
		LockableObject.unlock(this);
		try {
			root.urlSegment("ROOT_RESOURCE");
			root.resource(null);
		} catch (LockedValueException e) { e.printStackTrace(); };
		this.lock();
	}
	
	public synchronized ResourceUrl getResource(String url, String regex) throws LockedValueException {
		if(root == null)
			throw new NullPointerException("[ResourceTree.getResource(String,String):NO_RESOURCES_AVAILABLE] The root object and subsequencly any other resource objects has no been created and appended to this resource tree, because of this there are no resources to provide as of this moment.");
		if(!this.isLocked())
			throw new LockedValueException("[ResourceTree.getResource(String,String):INTERNAL_LOCK_NOT_SET] The resource tree lock must be set before attempting to search the tree.");
		String[] segs = url.split(regex);
		int i = 0;
		ResourceUrl current = root;
		while(current != null && segs.length > 0) {
			while(segs[i].equals(""))
				i++;
			ResourceUrl next = current.getNextUrl(segs[i]);
			if(next == null)
				return null;
			if(i >= segs.length-1)
				return next;
			current = next;
			i++;
		}
		return this.landing;
	}
	
	public String toString() {
		String str = "ResourceTree[";
		String indent = "   ";
		if(root == null)
			str += "\n"+indent+"root=null(NO_ROOT_OBJECT),";
		else
			str += "\n"+indent+"root="+root.toString(indent,indent);
		str += ",\n"+indent+"internalLock=Boolean["+this.isLocked()+"]\n";
		str += "]";
		return str;
	}
	
}
