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

import com.greydev.messenger.message.Message;
import com.greydev.messenger.message.comment.Comment;
import com.greydev.messenger.profile.Profile;

public class DatabaseMock {

	private static final Map<String, Profile> profileMap = new HashMap<>();
	private static SessionFactory factory = new Configuration()
			.configure("hibernate.cfg.xml")
			.buildSessionFactory();

	// saving some dummy messages and profiles to the database
	static {

		Message message1 = new Message("can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11));
		Message message2 = new Message("jason", "I own a grocery store!", new GregorianCalendar(2011, 04, 04));

		message1.getComments().add(new Comment("Johny", "First Comment", message1));
		message1.getComments().add(new Comment("Emily", "Grocery store", message1));

		message2.getComments().add(new Comment("Sally", "Hey there", message2));
		message2.getComments().add(new Comment("Sally2", "Hey there2", message2));

		DatabaseMock.addMessageHibernate(message1);
		DatabaseMock.addMessageHibernate(message2);

		//		System.out.println("-------------------------------- TEST ----------------------------");
		//		List<Message> test = getAllMessagesAsListHibernate();
		//		test.forEach(m -> System.out.println(m.getAuthor()));
		//
		//		Message one = getMessageHibernate(1L);
		//		Message four = getMessageHibernate(4L);
		//		System.out.println("one's author: " + one.getAuthor());
		//		System.out.println("four's author: " + four.getAuthor());
		//
		//		one.getCommentList().forEach(c -> System.out.println("comment: " + c.getText()));

		//		Message message4 = new Message(0L, "can", "Such a lovely weather today!", new GregorianCalendar(2015, 11, 11));
		//		Message message5 = new Message(1L, "jason", "I own a grocery store!");
		//		Profile profile1 = new Profile("can", "jk", "thx");
		//		Profile profile2 = new Profile("jason", "hey there", "Eyw");
		//
		//		DatabaseMock.addMessage(message4.getId(), message4);
		//		DatabaseMock.addMessage(message5.getId(), message5);
		//		DatabaseMock.addProfile(profile1.getProfileName(), profile1);
		//		DatabaseMock.addProfile(profile2.getProfileName(), profile2);
	}

	//	public static void addMessageHibernate(Message message) {
	//		final Session session = factory.openSession();
	//		Transaction transaction = null;
	//		try {
	//			transaction = session.beginTransaction();
	//
	//			System.out.println("DatabaseMock.addMessageHibernate() - Comment list in message: ");
	//			message.getCommentList().forEach(comment -> {
	//				System.out.println(comment.getAuthor());
	//				comment.setMessage(message);
	//				session.save(comment);
	//			});
	//
	//			session.save(message);
	//
	//			transaction.commit();
	//			session.close();
	//
	//		} catch (Exception e) {
	//			if (transaction != null) {
	//				transaction.rollback();
	//			}
	//			e.printStackTrace();
	//		}
	//	}

	public static Long addMessageHibernate(Message message) {
		Long savedEntityId = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			// this also saves the Comment collection inside message
			savedEntityId = (Long) session.save(message);

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

	public static List<Comment> getComments(Message message) {
		List<Comment> comments = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			comments = session.createQuery("from Comment where message.id=" + message.getId()).list();
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

	public static Message updateMessageHibernate(Message message) {
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			// first delete comments from the message which will be updated
			List<Comment> commentsToDelete = getComments(message);
			commentsToDelete.forEach(comment -> {
				System.out.println("Deleting comment ... " + comment.getAuthor());
				session.delete(comment);
			});
			// update/replace old message with the new one
			session.update(message);

			transaction.commit();
			session.close();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return null;
		}
		return message;

	}

	public static Message deleteMessageHibernate(Long id) {
		Message messageToDelete = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			messageToDelete = getMessageHibernate(id);
			if (messageToDelete != null) {
				session.delete(messageToDelete);
			}

			transaction.commit();
			session.close();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return messageToDelete;
	}

	public static Message getMessageHibernate(Long id) {
		Message result = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			result = session.get(Message.class, id);

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

	public static List<Message> getAllMessagesAsListHibernate() {
		List<Message> results = null;
		final Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			results = session.createQuery("from Message").list();

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

	public static List<Message> getAllMessagesForYear(int year) {
		List<Message> resultSet = new ArrayList<>();

		for (Message message : getAllMessagesAsListHibernate()) {
			if (message.getCreated().get(Calendar.YEAR) == year) {
				resultSet.add(message);
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
