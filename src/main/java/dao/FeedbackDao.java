package dao;

import javax.persistence.EntityManager;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import models.Feedback;
import models.FeedbackDto;

public class FeedbackDao {
	@Inject
	Provider<EntityManager> entityManagerProvider;
	
	@Transactional
	public boolean addFeedback(FeedbackDto feedbackDto, Long uid) {
		EntityManager entityManager = entityManagerProvider.get();
		
		try {
			Feedback feedback = new Feedback(uid, feedbackDto.message);
			entityManager.persist(feedback);
			return true;
		}
		catch (Exception e) {
			
			return false;
		}
	}

}
