package net.mcorp.common;

import net.mcorp.network.common.utils.debug.SmartDebug;

/**
 * <h1>PsudoFinalVariable</h1>
 * <hr>
 * <p>This class has the same effect as the final keyword however this class allows you to set the value in the future rather than at a objects construction.</p>
 * @author Andrew Kerr
 * @param <Obj> - The object class that this object should contain.
 */
public final class PsudoFinalVariable<Obj> extends SmartDebug{
	
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
	 * The object that is stored within this object.
	 */
	private Obj object;
	
	/**
	 * Returns {@linkplain PsudoFinalVariable#object}, this will return null if no value is set.
	 * @return {@linkplain Object ? extends Object} - The value set at {@linkplain PsudoFinalVariable#object}
	 */
	public Obj get() { return object; }
	
	/**
	 * Sets {@linkplain PsudoFinalVariable#object}, this action can only be done once.<br>
	 * Any further calls will throw a runtime exception.
	 * @param object - {@linkplain Object ? extends Object} - The value set at {@linkplain PsudoFinalVariable#object}
	 */
	public void set(Obj object) {
		if(this.isFinal() == true)
			throw new RuntimeException("[PsudoFinalVariable.set(Obj):OBJECT_FINAL] This object has already been set and cannot be set to another value.");
		this.object = object;
		this.isFinal = true;
	}

	@Override
	public String toString(String indent, String indentBy) {
		return this.getClass().getSimpleName()+"["
				+ "\n"+indent+indentBy+"isFinal = Boolean["+this.isFinal+"],"
				+ "\n"+indent+indentBy+"object = "+(object != null ? (object instanceof SmartDebug ? ((SmartDebug) object).toString(indent+indentBy, indentBy) : object.toString()) : null)
				+ "\n"+indent+"]";
	}
	
}
