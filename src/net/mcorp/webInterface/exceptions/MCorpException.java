package net.mcorp.webInterface.exceptions;

public class MCorpException extends Exception{

	private static final long serialVersionUID = -2932345827366603064L;

	public String getLocalizedMessage() { //TODO: Create Localization feature.
		return this.getMessage();
	}
	
	public String toString() {
		return this.toString("", "\t");
	}
	
	public String toString(String indent, String indentBy) {
		return this.toString(indent,indentBy,false);
	}
	
	public String toString(String indent, String indentBy, boolean indentFirst) {
		String str = "";
		str += (indentFirst ? indent : "")+this.getClass().getSimpleName()+"[";
		str += "\n"+indent+"message = String[\""+this.getLocalizedMessage()+"\"]";
		str += "\n"+(indentFirst ? indent : "")+"]";
		return str;
	}
	
}
