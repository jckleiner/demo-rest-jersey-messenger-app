package com.greydev.messenger.resource;

import javax.ws.rs.core.Response;

import com.greydev.messenger.resource.exception.DataNotFoundException;
import com.greydev.messenger.resource.model.ErrorMessage;

/* example exception mapper. Needs @Provider annotation to be activated.
	if (message == null) {
		throw new DataNotFoundException("message with id ... not found");
	}
	sends a response message with a custom error message.
*/
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {

		ErrorMessage errorMessage = new ErrorMessage(555, "such error", "much message");
		return Response.status(errorMessage.getErrorCode()).entity(errorMessage).build();
	}

}
