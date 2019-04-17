package com.greydev.messenger.resource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.greydev.messenger.resource.mockdb.DatabaseMock;
import com.greydev.messenger.resource.model.Comment;
import com.greydev.messenger.resource.model.Message;

public class CommentService {
	
	private Map<Long, Message> messageMap = DatabaseMock.getAllMessagesAsMap();
	
	public List<Comment> getAllComments(Long messageId) {
		Map<Long, Comment> comments = messageMap.get(messageId).getComments();
		List<Comment> resultCommentList = new ArrayList<>(comments.values());
		return resultCommentList;
	}
	
	public Comment getComment(Long messageId, Long commentId) {
		
		return null;
	}
	
	public Comment addComment(Long messageId, Comment comment) {
		
		return null;
	}
	
	public Comment updateComment(Long messageId,  Comment comment) {
		
		return null;
	}
	
	public Comment deleteComment(Long messageId, Long commentId) {
		
		return null;
	}
}
