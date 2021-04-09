package com.critical.example.sample_bitofcode.util;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.URI;

public class BasicResource implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MessageHelper messages;

	public Response ok(String msg) {
		return Response.ok().entity(new ResponseEntity(messages.getMessage(msg))).build();
	}

	public Response ok(String msg, Object data) {
		return Response.ok().entity(new ResponseEntity(messages.getMessage(msg), data)).build();
	}

	public Response ok(Object data) {
		return Response.ok().entity(data).build();
	}
	
	public Response ok() {
		return Response.ok().build();
	}

	public Response badRequest(String msg) {
		return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseEntity(messages.getMessage(msg))).build();
	}
	
	public Response badRequest(String msg, Exception ex) {
		return Response.status(Response.Status.BAD_REQUEST)
					.entity(new ResponseEntity(messages.getMessage(msg), ex))
					.build();
	}

	public Response notFound(String msg) {
		return Response.status(Response.Status.NOT_FOUND)
					.entity(new ResponseEntity(messages.getMessage(msg)))
					.build();
	}
	
	public Response notFound(String msg, Object data) {
		return Response.status(Response.Status.NOT_FOUND)
					.entity(new ResponseEntity(messages.getMessage(msg), data))
					.build();
	}

	public Response created(URI location, String msg, Object entity) {
		return Response.created(location)
					.entity(new ResponseEntity(messages.getMessage(msg), entity))
					.build();
	}

	public Response created(URI location, String msg) {
		return created(location, messages.getMessage(msg), null);
	}
	
	public Response conflict(String msg) {
		return Response.status(Response.Status.CONFLICT)
					.entity(new ResponseEntity(messages.getMessage(msg)))
					.build();
	}
	
	public Response conflict(String msg, Object data) {
		return Response.status(Response.Status.CONFLICT)
					.entity(new ResponseEntity(messages.getMessage(msg), data))
					.build();
	}

}