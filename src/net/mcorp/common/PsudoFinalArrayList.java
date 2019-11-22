package net.mcorp.common;

import java.util.ArrayList;
import java.util.Collection;

import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public class PsudoFinalArrayList<Obj extends Object> implements SmartDebugInterface{

	public ArrayList<Obj> list = new ArrayList<Obj>();
	
	/**
	 * A flag used to prevent further set calls.
	 */
	private boolean isFinal = false;
	
	/**
	 * Returns {@linkplain PsudoFinalVariable#isFinal}.
	 * @return {@linkplain Boolean} - If the internal variable has been set already.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Adds a object onto the list, this action can be performed untill the {@linkplain #done()} method is called, after that any calls to this method will result in a RuntimeException.
	 * @param object - {@linkplain Object ? extends Object} - The value set at {@linkplain PsudoFinalVariable#object}
	 * @return {@linkplain Boolean} - True as defined within the {@linkplain ArrayList#add(Object)} method.
	 */
	public boolean add(Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		list.add(object);
		return true;
	}
	
	public void add(int index, Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(int,Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		list.add(index,object);
	}
	
	public void set(int index, Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.set(int,Obj):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.add(index,object);
	}
	
	public void remove(Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.remove(Obj):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.remove(object);
	}
	
	public void remove(int index) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.remove(int):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.remove(index);
	}
	
	public Object[] toArray() {
		return list.toArray();
	}
	
	/**
	 * Sets the internal {@linkplain #isFinal} flag to true. This signals to the add methods that they can no longer add elements onto the list.
	 */
	public void publish() {
		this.isFinal = true;
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"isFinal = Boolean["+this.isFinal+"],"
				
				+ "\n"+indent+"]";
	}
}
