package net.mcorp.server.protocols.http;

/**
 * <style>
 * 	table,td,th{
 * 		border:1px #fff solid;
 * 	}
 * 	table{
 * 		width:70%;
 * 	}
 * 	.r{
 * 		color:red;
 * 		text-align:center;
 * 	}
 * 	.g{
 * 		color:green;
 * 		text-align:center;
 * 	}
 * </style>
 * <h1>HttpStatusCode(s)</h1>
 * <hr>
 * <p>
 * Http status codes are used by the server to respond to the client's
 * request. It should be noted that the response should contain a 
 * string after the number, however most if not all browsers only 
 * use this for displaying purposes only, the string has no real 
 * effect on how the client handles the response.
 * <br><br>
 * For Example:<br>
 * Http/1.1 <u><b>200</b></u> OK
 * <br><br>
 * In the example the number "200" indicates that the server understood
 * the request and attempted to fulfill it. See "200: Success".
 * <br><br>
 * <b>Note:</b><br>
 * The "IETF" (Internet Engineering Task Force) provides 
 * a lot of documentation about various different
 * protocols. If you wish to know more about what each code does
 * then click
 * <a href="https://tools.ietf.org/html/rfc2616#section-10.1.1">here</a>
 * or use one of the links left throughout the documentation.
 * </p>
 * <h2>1xx: Informational (<a href="https://tools.ietf.org/html/rfc2616#section-10.1">LINK</a>)</h2>
 * <p>
 * This series of codes indicates that a request was received 
 * and the client should begin processing.
 * </p>
 * <table>
 * 	<tr>
 * 		<th>Code</th><th>Name</th><th>Link</th>
 * 	</tr>
 * 	<tr>
 * 		<td>100</td>
 * 		<td>Continue</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.1.1">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>101</td>
 * 		<td>Switching Protocols</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.1.2">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>102-199</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td>No Link</td>
 * 	</tr>
 * </table>
 * 
 * <h2>2xx: Success (<a href="https://tools.ietf.org/html/rfc2616#section-10.2">LINK</a>)</h2>
 * <p>
 * This series of codes indicates that a request was successfully
 * received, understood, and accepted by the server.
 * </p>
 * <table>
 * 	<tr>
 * 		<th>Code</th><th>Name</th><th>Short Definition</th>
 * 	</tr>
 * 	<tr>
 * 		<td>200</td>
 * 		<td>OK</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.1">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>201</td>
 * 		<td>Created</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.2">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>202</td>
 * 		<td>Accepted</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.3">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>203</td>
 * 		<td>Non-Authoritative Information</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.4">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>204</td>
 * 		<td>No Content</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.5">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>205</td>
 * 		<td>Reset Content</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.6">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>206</td>
 * 		<td>Partial Content</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.2.7">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>207-299</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td>NO LINK</td>
 * 	</tr>
 * </table>
 * 
 * <h2>3xx: Redirection (<a href="https://tools.ietf.org/html/rfc2616#section-10.3">LINK</a>)</h2>
 * <p>
 * This series of codes indicates that further action must be taken
 * in order to complete the request.
 * </p>
 * <table>
 * 	<tr>
 * 		<th>Code</th><th>Name</th><th>Short Definition</th>
 * 	</tr>
 *  <tr>
 * 		<td>300</td>
 * 		<td>Multiple Choices</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.1">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>301</td>
 * 		<td>Moved Permanently</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.2">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>302</td>
 * 		<td>Found</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.3">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>303</td>
 * 		<td>See Other</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.4">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>304</td>
 * 		<td>Not Modified</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.5">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>305</td>
 * 		<td>Use Proxy</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.6">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>306</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.7">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>307</td>
 * 		<td>Temporary Redirect</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.3.8">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>308-399</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td>No Link</td>
 * 	</tr>
 * </table>
 * 
 * <h2>4xx: Client Error (<a href="https://tools.ietf.org/html/rgc2616#section-10.4">LINK</a>)</h2>
 * <p>
 * This series of codes indicates that a request contains bad syntax
 * or cannot be fulfilled.
 * </p>
 * <table>
 * 	<tr>
 * 		<th>Code</th><th>Name</th><th>Short Definition</th>
 * 	</tr>
 * 	<tr>
 * 		<td>400</td>
 * 		<td>Bad Request</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.1">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>401</td>
 * 		<td>Unauthorized</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.2">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>402</td>
 * 		<td>Payment Required</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.3">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>403</td>
 * 		<td>Forbidden</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.4">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>404</td>
 * 		<td>Not Found</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.5">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>405</td>
 * 		<td>Method Not Allowed</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.6">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>406</td>
 * 		<td>Not Acceptable</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.7">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>407</td>
 * 		<td>Proxy Authentication Required</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.8">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>408</td>
 * 		<td>Request Timeout</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.9">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>409</td>
 * 		<td>Conflict</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.10">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>410</td>
 * 		<td>Gone</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.11">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>411</td>
 * 		<td>Length Required</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.12">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>412</td>
 * 		<td>Precondition Failed</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.13">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>413</td>
 * 		<td>Request Entity Too Large</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.14">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>414</td>
 * 		<td>Request-URI Too Long</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.15">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>415</td>
 * 		<td>Unsupported Media Type</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.16">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>416</td>
 * 		<td>Requested Range Not Satisfiable</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.17">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>417</td>
 * 		<td>Expectation Failed</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.4.18">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>418-499</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td>No Link</td>
 * 	</tr>
 * </table>
 * 
 * <h2>5xx: Server Error (<a href="https://tools.ietf.org/html/rfc2616#section-10.5">LINK</a>)</h2>
 * <p>
 * This series of codes indicates that the server failed to fulfill
 * an apparently valid request.
 * </p>
 * <table>
 * 	<tr>
 * 		<th>Code</th><th>Name</th><th>Short Definition</th>
 * 	</tr>
 * 	<tr>
 * 		<td>500</td>
 * 		<td>Internal Server Error</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.1">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>501</td>
 * 		<td>Not Implemented</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.2">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>502</td>
 * 		<td>Bad Gateway</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.3">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>503</td>
 * 		<td>Service Unavailable</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.4">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>504</td>
 * 		<td>Gateway Timeout</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.5">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>505</td>
 * 		<td>Http Version Not Supported</td>
 * 		<td><a href="https://tools.ietf.org/html/rfc2616#section-10.5.6">LINK</a></td>
 * 	</tr>
 * 	<tr>
 * 		<td>506-599</td>
 * 		<td class="r">Not Used Commonly</td>
 * 		<td>No Link</td>
 * 	</tr>
 * </table>
 * <hr>
 * <p>
 * 	<b><u>Note:</u></b><br>
 * 	It should be noted that if the name is <span style="color:red">Not Used Commonly</span>
 * 	then it simply indicates that it is not a standardized code: However
 * 	that does not mean that they go unused, many different applications
 * 	will use these codes for there own purposes.
 * </p>
 * @author Andrew Kerr
 */
public enum StandardHttpStatusCodes implements HttpStatusCode{
	
	//1xx: Information
	Continue(100),
	Switch_Protocol(101),
	
	//2xx: Success
	Ok(200),
	Created(201),
	Accepted(202),
	Non_Authoritative_Info(203),
	No_Content(204),
	Reset_Content(205),
	Partial_Content(206),
	
	//3xx: Redirection
	Multiple_Choices(300),
	Moved_Permanently(301),
	Found(302),
	See_Other(303),
	Not_Modified(304),
	Use_Proxy(305),
	Temporary_Redirect(307),
	
	//4xx: Client Error
	Bad_Request(400),
	Unauthorized(401),
	Payment_Required(402),
	Forbidden(403),
	Not_Found(404),
	Method_Not_Allowed(405),
	Not_Acceptable(406),
	Proxy_Authentication_Required(407),
	Request_Timeout(408),
	Conflict(409),
	Gone(410),
	Length_Required(411),
	Precondition_Failed(412),
	Request_Entity_Too_Large(413),
	Request_URI_Too_Long(414),
	Unsupported_Media_Type(415),
	Requested_Range_Not_Satisfiable(416),
	Expectation_Failed(417),
	
	//5xx: Server Error
	Internal_Server_Error(500),
	Not_Implemented(501),
	Bad_Gateway(502),
	Service_Unavailable(503),
	Gateway_Timeout(504),
	Http_Version_Not_Supported(505)
	
	;
	
	private final int code;
	private StandardHttpStatusCodes(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getText() {
		return this.name().replace('_', ' ');
	}
	
}
