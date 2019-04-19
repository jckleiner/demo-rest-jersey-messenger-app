package com.greydev.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidRequestDataExceptionMapper implements ExceptionMapper<InvalidRequestDataException> {

	@Override
	public Response toResponse(InvalidRequestDataException ex) {
		return Response.status(ex.getStatusCode()).entity(new ErrorMessage(ex)).build();
	}

}
