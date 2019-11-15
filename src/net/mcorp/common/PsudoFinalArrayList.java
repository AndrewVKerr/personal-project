package net.mcorp.common;

import java.util.ArrayList;
import java.util.Collection;

import net.mcorp.network.common.utils.debug.SmartDebugInterface;

public final class PsudoFinalArrayList<Obj> extends ArrayList<Obj> implements SmartDebugInterface{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2736261668533375744L;

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
		super.add(object);
		return true;
	}
	
	public void add(int index, Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(int,Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		super.add(index,object);
	}
	
	public boolean addAll(Collection<? extends Obj> objects) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		super.addAll(objects);
		return true;
	}
	
	/**
	 * Sets the internal {@linkplain #isFinal} flag to true. This signals to the add methods that they can no longer add elements onto the list.
	 */
	public void done() {
		this.isFinal = true;
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"isFinal = Boolean["+this.isFinal+"],"
				
				+ "\n"+indent+"]";
	}
}
