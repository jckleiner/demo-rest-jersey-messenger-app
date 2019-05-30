package com.greydev.messenger.post.comment;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.greydev.messenger.database.SessionFactorySingleton;
import com.greydev.messenger.post.Post;

public class CommentDao {

	private static SessionFactory factory = SessionFactorySingleton.getSessionFactory();

	public static List<Comment> getCommentsForPost(Post post) {
		List<Comment> comments = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			comments = session.createQuery("from Comment where post.id=" + post.getId()).list();
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

}
