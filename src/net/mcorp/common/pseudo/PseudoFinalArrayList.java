package net.mcorp.common.pseudo;

import java.util.ArrayList;
import java.util.Collection;

import net.mcorp.common.utils.debug.SmartDebug;
import net.mcorp.common.utils.debug.SmartDebugInterface;

/**
 * <h1>net.mcorp.common.pseudo.PseudoFinalArrayList&lt;Obj extends Object&gt;</h1><hr>
 * <p>
 * 	This class contains an {@linkplain Arraylist} that contains {@linkplain Obj} objects. Upon calling the {@linkplain #publish()} method the methods
 * 	{@linkplain #add(Object)}, {@linkplain #add(int, Object)}, {@linkplain #set(int, Object)}, {@linkplain #remove(int)}, {@linkplain #remove(Object)}
 *  will all be disabled and will throw {@linkplain RuntimeExceptions} instead of performing their actions.
 * </p>
 * <hr>
 * @see PseudoFinalVariable
 * @author Andrew Kerr
 * @param <Obj> - {@linkplain Object ? extends Object} - The class of an object that this class will contain.
 */
public class PseudoFinalArrayList<Obj extends Object> implements SmartDebugInterface{

	public ArrayList<Obj> list = new ArrayList<Obj>();
	
	/**
	 * A flag used to prevent further set calls.
	 */
	private boolean isFinal = false;
	
	/**
	 * Returns {@linkplain PseudoFinalVariable#isFinal}.
	 * @return {@linkplain Boolean} - If the internal variable has been set already.
	 */
	public synchronized boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * Adds a object onto the list, this action can be performed untill the {@linkplain #done()} method is called, after that any calls to this method will result in a RuntimeException.
	 * @param object - {@linkplain Object ? extends Object} - The value set at {@linkplain PseudoFinalVariable#object}
	 * @return {@linkplain Boolean} - True as defined within the {@linkplain ArrayList#add(Object)} method.
	 */
	public synchronized boolean add(Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		list.add(object);
		return true;
	}
	
	public synchronized void add(int index, Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.add(int,Obj):FINAL] The internal isFinal flag has been set to true, no more elements may be added onto the list.");
		list.add(index,object);
	}
	
	public synchronized void set(int index, Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.set(int,Obj):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.add(index,object);
	}
	
	public synchronized void remove(Obj object) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.remove(Obj):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.remove(object);
	}
	
	public synchronized void remove(int index) {
		if(this.isFinal == true)
			throw new RuntimeException("[PsudoFinalVariable.remove(int):FINAL] The internal isFinal flag has been set to true, no more changes may be done to the list.");
		list.remove(index);
	}
	
	public synchronized Object[] toArray() {
		return list.toArray();
	}
	
	public synchronized Object[] toArray(Class<? extends Obj> class_) {
		Object[] objs = new Object[this.list.size()];
		int i = 0;
		for(Obj obj : this.list) {
			if(obj.getClass() == class_) {
				objs[i] = obj;
				i++;
			}
		}
		Object[] finObjs = new Object[objs.length];
		for(int j = 0; j <= objs.length; j++) {
			finObjs[j] = objs[j];
		}
		return finObjs;
	}
	
	/**
	 * Sets the internal {@linkplain #isFinal} flag to true. This signals to the add methods that they can no longer add elements onto the list.
	 */
	public synchronized void publish() {
		this.isFinal = true;
	}

	@Override
	public synchronized String toString(String indent, String indentBy) {
		String s = this.getClass().getSimpleName()+"[";
		s += "\n"+indent+indentBy+"isFinal = Boolean["+this.isFinal+"],";
		s += "\n"+indent+indentBy+"values = {";
		for(Obj obj : list) {
			s += "\n"+indent+indentBy+indentBy+SmartDebugInterface.readSmartDebug(indent+indentBy+indentBy, indentBy, obj)+",";
		}
		s += "\n"+indent+indentBy+"}";
		s += "\n"+indent+"]";
		return s;
	}
}
