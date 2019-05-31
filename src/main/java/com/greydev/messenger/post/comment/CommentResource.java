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

@Path("/")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class CommentResource {

	private static final Logger LOG = LoggerFactory.getLogger(CommentResource.class);

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("postId") Long postId) {
		LOG.info("getAllComments");
		return commentService.getAllComments(postId);
	}

	@GET
	@Path("{commentId}")
	public Comment getComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId) {
		return commentService.getComment(postId, commentId);
	}

	@POST
	public Comment addComment(@PathParam("postId") Long postId, Comment comment) {
		return commentService.addComment(postId, comment);
	}

	@PUT
	@Path("{commentId}")
	public Comment updateComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId,
			Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(postId, comment);
	}

	@DELETE
	@Path("{commentId}")
	public Comment deleteComment(@PathParam("postId") Long postId, @PathParam("commentId") Long commentId) {
		return commentService.deleteComment(postId, commentId);
	}

}
