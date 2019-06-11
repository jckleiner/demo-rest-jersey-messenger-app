package com.greydev.messenger.post.comment;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.post.PostDao;

// TODO writing URI's manually inside exceptions seems bad, fix
public class CommentService {

	public List<Comment> getAllCommentsForEveryPost() {
		List<Comment> comments = CommentDao.getAllCommentsForEveryPost();
		return comments;
	}

	public List<Comment> getCommentsForPost(Long postId) {
		List<Comment> comments = CommentDao.getCommentsForPost(postId);
		return comments;
	}

	public Comment getComment(Long postId, Long commentId) {
		Comment comment = CommentDao.getComment(commentId);
		return comment;
	}

	public Comment getComment(Long commentId) {
		Comment comment = CommentDao.getComment(commentId);
		return comment;
	}

	public Comment addComment(Long postId, Comment comment) throws InvalidRequestDataException, DataNotFoundException {
		if (!isCommentValid(comment)) {
			throw new InvalidRequestDataException("POST", "/posts/" + postId + "/comments/");
		}
		if (!doesPostExist(postId)) {
			throw new DataNotFoundException("POST", "/posts/" + postId + "/comments/");
		}
		comment.setId(null);
		comment.setCreated(new GregorianCalendar());
		CommentDao.addCommentToPost(postId, comment);
		return comment;
	}

	// TODO 
	public Comment addCommentNewEndpoint(Comment comment) throws InvalidRequestDataException, DataNotFoundException {
		Long postId = comment.getParentPostId();
		return addComment(postId, comment);

	}

	public Comment updateComment(Long postId, Long commentId, Comment comment)
			throws DataNotFoundException, InvalidRequestDataException {

		comment.setId(commentId); // use the given comment id from the URL
		if (!isCommentValid(comment)) {
			throw new InvalidRequestDataException("PUT", "/posts/" + postId + "/comments/" + commentId);
		}
		System.out.println("POST ID: " + postId);
		System.out.println(doesPostExist(postId));
		if (!doesPostExist(postId)) {
			throw new DataNotFoundException("PUT", "/posts/" + postId);
		}
		/* if there is no comment with the given id, add a new comment with that id
		 * We use a custom id generator, so if the Entity has already an id set,
		 * it will use that when saving it to the DB. If it's null then a new one is generated.
		 */
		// TODO throw exception from DB or check here for null???
		if (!doesCommentExist(comment.getId())) {
			CommentDao.addCommentToPost(postId, comment);
			return comment;
		}
		comment = CommentDao.updateComment(postId, comment);
		return comment;
	}

	public Comment deleteComment(Long postId, Long commentId) throws DataNotFoundException {
		Comment deletedComment = CommentDao.deleteComment(commentId);
		if (deletedComment == null) {
			throw new DataNotFoundException("DELETE", "posts/" + postId + "/comments/" + commentId);
		}
		return deletedComment;
	}

	// mandatory properties: Author, Text
	public boolean isCommentValid(Comment comment) {
		return StringUtils.isNoneBlank(comment.getAuthor(), comment.getText());
	}

	public boolean doesCommentExist(Long id) {
		return (CommentDao.getComment(id) != null);
	}

	public boolean doesPostExist(Long id) {
		return (PostDao.getPost(id) != null);
	}

}
