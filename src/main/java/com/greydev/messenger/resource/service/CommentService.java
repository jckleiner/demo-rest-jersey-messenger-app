package com.greydev.messenger.resource.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.greydev.messenger.resource.database.DatabaseMock;
import com.greydev.messenger.resource.model.Comment;
import com.greydev.messenger.resource.model.Message;

//TODO add error handling
public class CommentService {
	
	private Map<Long, Message> messageMap = DatabaseMock.getAllMessagesAsMap();
	private static long idCount = 150;
	
	public List<Comment> getAllComments(Long messageId) {
		Map<Long, Comment> comments = messageMap.get(messageId).getComments();
		List<Comment> resultCommentList = new ArrayList<>(comments.values());
		return resultCommentList;
	}
	
	public Comment getComment(Long messageId, Long commentId) {
		return messageMap.get(messageId).getComments().get(commentId);
	}
	
	public Comment addComment(Long messageId, Comment comment) {
		comment.setId(getNextId());
		comment.setCreated(new GregorianCalendar());
		messageMap.get(messageId).getComments().put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(Long messageId,  Comment comment) {
		comment.setCreated(new GregorianCalendar());
		Map<Long, Comment> allComments = messageMap.get(messageId).getComments();
		allComments.put(comment.getId(), comment);
		return comment;
	}

	public Comment deleteComment(Long messageId, Long commentId) {
		Map<Long, Comment> allComments = messageMap.get(messageId).getComments();
		return allComments.remove(commentId);
	}
	
	private static long getNextId() {
		return idCount++;
	}
}
