package com.greydev.messenger.post.comment;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;

@Path("/")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CommentResource {

	private static final Logger LOG = LoggerFactory.getLogger(CommentResource.class);

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getCommentsForPost(@PathParam("postId") Long postId) {
		LOG.info("getCommentsForPost");
		return commentService.getCommentsForPost(postId);
	}

	@GET
	@Path("{commentId}")
	//TODO post id is unused?
	public Comment getComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId) {
		return commentService.getComment(postId, commentId);
	}

	@POST
	public Comment addComment(@PathParam("postId") Long postId, Comment comment)
			throws InvalidRequestDataException, DataNotFoundException {
		return commentService.addComment(postId, comment);
	}

	@PUT
	@Path("{commentId}")
	public Comment updateComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId,
			Comment comment) throws DataNotFoundException, InvalidRequestDataException {
		return commentService.updateComment(postId, commentId, comment);
	}

	@DELETE
	@Path("{commentId}")
	public Comment deleteComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId)
			throws DataNotFoundException {
		return commentService.deleteComment(postId, commentId);
	}

}
