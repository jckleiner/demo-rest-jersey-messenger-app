package com.greydev.messenger.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.greydev.messenger.post.Post;
import com.greydev.messenger.post.comment.Comment;
import com.greydev.messenger.profile.Profile;

public class DatabaseMock {

	private static final Map<String, Profile> profileMap = new HashMap<>();
	private static SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.buildSessionFactory();

	// saving some dummy posts and profiles to the database
	static {

		Post post1 = new Post("can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11));
		Post post2 = new Post("jason", "I own a grocery store!", new GregorianCalendar(2011, 04, 04));

		post1.getComments().add(new Comment("Johny", "First Comment", post1));
		post1.getComments().add(new Comment("Emily", "Grocery store", post1));

		post2.getComments().add(new Comment("Sally", "Hey there", post2));
		post2.getComments().add(new Comment("Sally2", "Hey there2", post2));

		DatabaseMock.addPostHibernate(post1);
		DatabaseMock.addPostHibernate(post2);

	}

	public static Long addPostHibernate(Post post) {
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

	public static List<Comment> getComments(Post post) {
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

	public static Post updatePostHibernate(Post post) {
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			// first delete comments from the post object which will be updated
			List<Comment> commentsToDelete = getComments(post);
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

	public static Post deletePostHibernate(Long id) {
		Post postToDelete = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			postToDelete = getPostHibernate(id);
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

	public static Post getPostHibernate(Long id) {
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

	public static List<Post> getAllPostsAsListHibernate() {
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

		for (Post post : getAllPostsAsListHibernate()) {
			if (post.getCreated().get(Calendar.YEAR) == year) {
				resultSet.add(post);
			}
		}
		return resultSet;
	}

	public static List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profileMap.values());
	}

	public static Profile getProfile(String profileName) {
		return profileMap.get(profileName);
	}

	public static void addProfile(String profileName, Profile profile) {
		// limit capacity
		if (getAllProfiles().size() >= 200) {
			return;
		}
		profileMap.put(profileName, profile);
	}

	public static Profile deleteProfile(String profileName) {
		return profileMap.remove(profileName);
	}

	public static Profile updateProfile(Profile profile) {
		return profileMap.replace(profile.getProfileName(), profile);
	}

}
