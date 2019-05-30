package com.greydev.messenger.post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.greydev.messenger.database.SessionFactorySingleton;
import com.greydev.messenger.post.comment.Comment;
import com.greydev.messenger.post.comment.CommentDao;

public class PostDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();

	// saving some dummy posts and profiles to the database
	static {

		Post post1 = new Post("can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11));
		Post post2 = new Post("jason", "I own a grocery store!", new GregorianCalendar(2011, 04, 04));

		post1.getComments().add(new Comment("Johny", "First Comment", post1));
		post1.getComments().add(new Comment("Emily", "Grocery store", post1));

		post2.getComments().add(new Comment("Sally", "Hey there", post2));
		post2.getComments().add(new Comment("Sally2", "Hey there2", post2));

		addPost(post1);
		addPost(post2);

	}

	public static Long addPost(Post post) {
		Long savedEntityId = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// this also saves the Comment collection inside the post object
			savedEntityId = (Long) session.save(post);

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return savedEntityId;
	}

	public static Post updatePost(Post post) {
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			// first delete comments from the post object which will be updated
			List<Comment> commentsToDelete = CommentDao.getCommentsForPost(post);
			commentsToDelete.forEach(comment -> {
				System.out.println("Deleting comment ... " + comment.getAuthor());
				session.delete(comment);
			});
			// update/replace old post object with the new one
			session.update(post);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return null;
		}
		return post;

	}

	public static Post deletePost(Long id) {
		Post postToDelete = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			postToDelete = getPost(id);
			if (postToDelete != null) {
				session.delete(postToDelete);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return postToDelete;
	}

	public static Post getPost(Long id) {
		Post result = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			result = session.get(Post.class, id);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return result;

	}

	public static List<Post> getAllPostsAsList() {
		List<Post> results = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			results = session.createQuery("from Post").list();

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

	public static List<Post> getAllPostsForYear(int year) {
		List<Post> resultSet = new ArrayList<>();

		for (Post post : getAllPostsAsList()) {
			if (post.getCreated().get(Calendar.YEAR) == year) {
				resultSet.add(post);
			}
		}
		return resultSet;
	}

}
