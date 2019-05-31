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

	public static List<Comment> getAllCommentsForEveryPost() {
		List<Comment> comments = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			comments = session.createQuery("from Comment").list();
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

			// DON'T FORGET to handle the bi-directional relationship!
			Post parentPost = session.get(Post.class, postId);
			newComment.setPost(parentPost); // without this, FK 'post.id' will be null
			session.clear(); // throws an exception without this line
			session.update(newComment); // finds the old comment from the id and replaces it

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

			// TODO how to handle this properly?
			commentToDelete = getComment(id);
			if (commentToDelete != null) {
				// removes the comment both from the commentList inside Post
				// a
				session.delete(commentToDelete);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			commentToDelete = null;
		}
		return commentToDelete;
	}

}
