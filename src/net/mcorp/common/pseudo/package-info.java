/**
 * <h1>net.mcorp.common.pseudo</h1><hr>
 * <p>
 * 	This package contains pseudo final objects. These objects were created because there was a need for objects that could be initialized with
 * 	a final keyword but maybe didn't necessarily need to be defined at construction.
 *  <hr>
 *  Each object in this package has a unique way of finalizing their internal structure. Look into the class definition javadoc (displayed below) for
 *  instructions on how to interact with the objects and how they finalize their internal structure.
 * 	<hr>
 * 	<ul>
 * 		<li>{@linkplain net.mcorp.common.pseudo.PseudoFinalVariable}&lt;? extends {@linkplain Object}&gt; - A generic object that stands in for another object.</li>
 * 		<li>{@linkplain net.mcorp.common.pseudo.PseudoFinalArrayList}&lt;? extends {@linkplain Object}&gt; - A generic object that interfaces with an ArrayList.</li>
 * 		<li>{@linkplain net.mcorp.common.pseudo.PseudoFinalException}&lt;? extends {@linkplain Exception}&gt; - A generic object that stands in for another exception.</li>
 * 	</ul>
 * </p>
 * <hr>
 * @author Andrew Kerr
 * @version 1.0.0
 * @apiNote Each object must be stored with the final keyword, otherwise these objects are useless due to the fact that you could just store a new 
 * instance of these classes in their place.
 */
package net.mcorp.common.pseudo;