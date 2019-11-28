package net.mcorp.common.pseudo;

import net.mcorp.common.utils.debug.SmartDebug;

/**
 * <h1>net.mcorp.common.pseudo.PsudoFinalVariable&lt;Obj&gt;</h1>
 * <hr>
 * <p>
 * 	This class is used in conjunction with the final keyword to allow a object to benefit from the final keyword whilst also benefiting from
 * 	the ability to set anywhere in the code rather than at an objects construction.
 * </p>
 * <hr>
 * Variables:
 * <ul>
 * 	<li>
 * 		{@linkplain #isFinal} - {@linkplain Boolean} - Has the object had its {@linkplain #object} set yet? 
 * 	</li>
 * 	<li>
 * 		{@linkplain #object} - {@linkplain Obj} - The internal object being stored.
 * 	</li>
 * </ul>
 * <hr>
 * Methods:
 * <ul>
 * 	<li>
 * 		{@linkplain #isFinal()} - {@linkplain Boolean} - Returns the boolean value stored at {@linkplain #isFinal}.
 * 	</li>
 * 	<li>
 * 		{@linkplain #set(Object)} - void - Sets the internal value ({@linkplain #object}) and sets {@linkplain #isFinal} to {@linkplain Boolean#TRUE}.
 * 	</li>
 * 	<li>
 * 		{@linkplain #get()} - {@linkplain Obj} - Returns the object stored at {@linkplain #object}.
 * 	</li>
 * </ul>
 * <hr>
 * @author Andrew Kerr
 * @param <Obj> - {@linkplain Object ? extends Object} - The class of an object that this class will contain.
 * @see SmartDebug
 */
public class PseudoFinalVariable<Obj> extends SmartDebug{
	
	/**
	 * A flag used to prevent further set calls.
	 */
	private boolean isFinal = false;
	
	/**
	 * Returns {@linkplain PseudoFinalVariable#isFinal}.
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
	 * Returns {@linkplain PseudoFinalVariable#object}, this will return null if no value is set.
	 * @return {@linkplain Object ? extends Object} - The value set at {@linkplain PseudoFinalVariable#object}
	 */
	public Obj get() { return object; }
	
	/**
	 * Sets {@linkplain PseudoFinalVariable#object}, this action can only be done once.<br>
	 * Any further calls will throw a runtime exception.
	 * @param object - {@linkplain Object ? extends Object} - The value set at {@linkplain PseudoFinalVariable#object}
	 * @throws RuntimeException This method is a one time call, any further calls will result in a runtime exception.
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
