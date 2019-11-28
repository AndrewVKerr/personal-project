package net.mcorp.common.sync;

/**
 * <h1>mnet.mcorp.common.sync.SynchronizedVariable&lt;Obj&gt;</h1>
 * <hr>
 * <p>
 * 	This class acts exactly like the {@linkplain java.util.concurrent.atomic.AtomicReference} class. However it has been simplified down to four
 * 	simple functions.
 * 	<hr>
 * 	<ul>
 * 		<li>{@linkplain #SynchronizedVariable(Object)} - Constructor - Sets the initial value of the object.</li>
 * 		<li>{@linkplain #SynchronizedVariable()} - Constructor - Sets the initial value of the object to null.</li>
 * 		<li>{@linkplain #get()} - {@linkplain Obj} - Retrieves the stored object ({@linkplain #object}).</li>
 * 		<li>{@linkplain #set(Obj)} - {@linkplain Obj} - Sets the stored object ({@linkplain #object}) to the provided parameter.</li>
 * 	</ul>
 * </p>
 * <hr>
 * @author Andrew Kerr
 * @param <Obj> - {@linkplain Object ? extends Object} - The class of the object that this class will be synchronizing.
 */
public class SynchronizedVariable<Obj extends Object>{
	
	/**
	 * The object being synchronized.
	 */
	private Obj object;
	
	public SynchronizedVariable(Obj init) {
		this.object = init;
	}
	
	public SynchronizedVariable() {}

	/**
	 * Retrieves the stored {@linkplain SynchronizedVariable#object} while blocking any further {@linkplain #set(Object)} and {@linkplain #get()} methods in
	 * every thread.
	 * @return {@linkplain Obj} - The stored {@linkplain #object}.
	 */
	public synchronized Obj get() {
		return this.object;
	}
	
	/**
	 * Sets the stored {@linkplain SynchronizedVariable#object} while blocking any further {@linkplain #set(Object)} and {@linkplain #get()} methods in
	 * every thread.
	 * @param object - {@linkplain Obj} - the object to store in {@linkplain SynchronizedVariable#object}.
	 */
	public synchronized void set(Obj object) {
		this.object = object;
	}
	
}
