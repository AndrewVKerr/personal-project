/**
 * <h1>net.mcorp.network.common.protocols.encryption.ssl</h1>
 * <hr>
 * <h2>What is SSL?</h2>
 * <p>
 * 	SSL (Secured Socket Layer) is a encryption protocol that is applied on top of whatever protocol is currently in use.
 * </p>
 * <hr>
 * <h2>How does it work?</h2>
 * <p>
 * 	I wont get into specifics but I will explain in layman terms how it works. I will also leave a link 
 * 	<a href="https://hpbn.co/transport-layer-security-tls/">HERE</a>. The basics of SSL are simple,
 * 	create a secured connection via a trusted third party's authentication and use the secured connection
 * 	to transfer information between two networked computers.
 * </p>
 * <hr>
 * <h2>So how do I use this module?</h2>
 * <p>
 * 	Use of this module is simple, the first part is creating a {@linkplain SSLConnection} object and passing it a prior {@linkplain Connection} object.
 * 	This will generate a hidden layer that will be responsible for maintaining the connection and for passing data to and from the socket object contained
 * 	within the provided Connection object. Then simply get the input/output stream from the SSLConnection. It will handle the rest for you.
 * </p>
 * <hr>
 * @author Andrew Kerr
 */
package net.mcorp.network.common.protocols.encryption.ssl;