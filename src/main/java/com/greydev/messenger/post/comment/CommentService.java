package com.greydev.messenger.post.comment;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.greydev.messenger.post.Post;

//TODO add error handling
public class CommentService {

	private Map<Long, Post> messageMap = new HashMap<>();
	private static long idCount = 10;

	public List<Comment> getAllComments(Long messageId) {
		//		Map<Long, Comment> comments = messageMap.get(messageId).getComments();
		//		List<Comment> resultCommentList = new ArrayList<>(comments.values());
		//		return resultCommentList;
		return null;
	}

	public Comment getComment(Long messageId, Long commentId) {
		//		return messageMap.get(messageId).getComments().get(commentId);
		return null;
	}

	public Comment addComment(Long messageId, Comment comment) {
		comment.setId(getNextId());
		comment.setCreated(new GregorianCalendar());
		// limit capacity
		if (messageMap.size() >= 200) {
			return null;
		}
		//		messageMap.get(messageId).getComments().put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(Long messageId, Comment comment) {
		comment.setCreated(new GregorianCalendar());
		//		Map<Long, Comment> allComments = messageMap.get(messageId).getComments();
		//		allComments.put(comment.getId(), comment);
		//		return comment;
		return null;
	}

	public Comment deleteComment(Long messageId, Long commentId) {
		//		Map<Long, Comment> allComments = messageMap.get(messageId).getComments();
		//		return allComments.remove(commentId);
		return null;
	}

	private static long getNextId() {
		return idCount++;
	}
}
