package com.greydev.messenger.post.comment;

import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.greydev.messenger.exception.DataNotFoundException;
import com.greydev.messenger.exception.InvalidRequestDataException;
import com.greydev.messenger.post.PostDao;

//TODO add error handling
public class CommentService {

	// TODO get ALL comments

	public List<Comment> getCommentsForPost(Long postId) {
		List<Comment> results = CommentDao.getCommentsForPost(postId);
		return results;
	}

	public Comment getComment(Long messageId, Long commentId) {
		return CommentDao.getComment(commentId);
	}

	public Comment addComment(Long postId, Comment comment) throws InvalidRequestDataException, DataNotFoundException {
		if (!isCommentValid(comment)) {
			throw new InvalidRequestDataException("POST", "/comments");
		}
		if (!doesPostExist(postId)) {
			throw new DataNotFoundException("POST", "/posts/" + postId);
		}
		comment.setId(null);
		comment.setCreated(new GregorianCalendar());
		CommentDao.addCommentToPost(postId, comment);
		return comment;
	}

	public Comment updateComment(Long postId, Comment comment) throws DataNotFoundException, InvalidRequestDataException {

		if (!isCommentValid(comment)) {
			throw new InvalidRequestDataException("PUT", "/comments");
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
		return CommentDao.updateComment(postId, comment);
	}

	public Comment deleteComment(Long messageId, Long commentId) {
		return CommentDao.deleteComment(commentId);
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
