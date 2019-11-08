/**
 * <h1>Http Status Codes</h1>
 * <hr>
 * <h2>What are Http Status Codes?</h2>
 * <p>
 * 	The HTTP protocol created a structured list of codes called "HTTP Status Codes".
 * 	These codes were created to give a general purpose to a HTTP packet. Each code
 * 	determines how the client should react to the information provided (If provided).
 * 	Each code is marked by a 3 digit number (e.g 000-999): However only the numbers 
 * 	between 100-599 are used (officially) as of 11/8/19.
 * </p>
 * <hr>
 * <h2>So how do I know which code to use?</h2>
 * <p>
 * 	The codes are divided into 5 official sub-categories each one has a general idea
 * 	that is used to give you an idea on what code range to use. (See referenced classes at bottom.)
 * 	<ul>
 * 		<li>Informational (100-199)</li>
 * 		<li>Success (200-299)</li>
 * 		<li>Redirection (300-399)</li>
 * 		<li>Client Error (400-499)</li>
 * 		<li>Server Error (500-599)</li>
 * 	</ul>
 * </p>
 * @see InformationalStatusCodes
 * @see SuccessStatusCodes
 * @see RedirectionStatusCodes
 * @see ClientErrorStatusCodes
 * @see ServerErrorStatusCodes
 * @see StatusCode
 * @see ErrorStatusCodes
 */
package net.mcorp.network.common.protocols.http.codes;