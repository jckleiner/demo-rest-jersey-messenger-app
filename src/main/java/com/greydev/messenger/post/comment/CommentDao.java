package com.greydev.messenger.post.comment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.greydev.messenger.SessionFactorySingleton;
import com.greydev.messenger.post.Post;

public class CommentDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();

	public static Comment getComment(Long id) {
		Comment result = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			result = session.get(Comment.class, id);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			result = null;
		}
		return result;
	}

	public static List<Comment> getCommentsForPost(Long postId) {
		List<Comment> comments = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			comments = session.createQuery("from Comment where post.id=" + postId).list();
			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return comments;
	}

	public static List<Comment> getAllCommentsAsList() {
		List<Comment> results = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			results = session.createQuery("from Comment").list();

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return results;
	}

	public static void addCommentToPost(Long postId, Comment comment) {
		System.out.println(" ADDING A COMMENT...");
		// TODO add comment date automatically
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			/* Hibernate keeps track of persistent objects during transactions
			 * Updating the Post object will automatically trigger an update in the database. 
			 * That's why we don't need to: .save(parentPost) or .save(comment)
			 */
			Post parentPost = session.get(Post.class, postId);
			// DON'T FORGET to handle the bi-directional relationship!
			parentPost.getComments().add(comment);
			session.save(comment);
			comment.setPost(parentPost);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	public static Comment updateComment(Long postId, Comment newComment) {
		System.out.println("  INSIDE UPDATE  ");
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// TODO can we set a new parent???
			//			session.update(comment);

			Post parentPost = session.get(Post.class, postId);
			// DON'T FORGET to handle the bi-directional relationship!
			newComment.setPost(parentPost);

			List<Comment> comments = parentPost.getComments();
			// delete old comment from the list
			comments.forEach(comm -> {
				if (comm.getId() == newComment.getId()) {
					comments.remove(comm);
					comments.add(newComment);
				}
			});

			// TODO successfully update an existing comment.

			// A different object with the same identifier value was already associated with the session : [com.greydev.messenger.post.comment.Comment#55]

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return null;
		}
		return newComment;

	}

	public static Comment deleteComment(Long id) {
		Comment commentToDelete = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return commentToDelete;
	}

}
