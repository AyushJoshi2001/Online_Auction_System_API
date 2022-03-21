package controllers;

import com.google.inject.Inject;

import dao.FeedbackDao;
import models.FeedbackDto;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

public class FeedbackController {
	@Inject
	FeedbackDao feedbackDao;

	public Result addFeedback(FeedbackDto feedbackDto, @PathParam("uid") Long uid) {
		boolean feedback = feedbackDao.addFeedback(feedbackDto, uid);
		if(feedback) {			
			return Results.json().render("Feedback submitted");
		}
		return Results.badRequest().json().render("Bad Request");
	}
}
