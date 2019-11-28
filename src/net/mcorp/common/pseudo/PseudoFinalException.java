package net.mcorp.common.pseudo;

/**
 * <h1>net.mcorp.common.pseudo.PseudoFinalException&lt;Obj extends Exception&gt;</h1><hr>
 * <p>
 * 	This class is essentially the same as {@linkplain PseudoFinalVariable}. This class exists solely for backwards compatibility, and is planned
 * 	to be removed later.
 * </p>
 * <hr>
 * @see PseudoFinalVariable
 * @author Andrew Kerr
 * @param <Obj> - {@linkplain Exception ? extends Exception} - The class of an exception that this class will contain.
 */
public class PseudoFinalException<Obj extends Exception> extends PseudoFinalVariable<Obj> {}
