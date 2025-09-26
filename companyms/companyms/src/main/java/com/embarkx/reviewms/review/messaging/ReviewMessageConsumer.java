package com.embarkx.reviewms.review.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.embarkx.companyms.company.CompanyService;
import com.embarkx.reviewms.review.dto.ReviewMessage;

public class ReviewMessageConsumer {
	
	private final CompanyService companyService;

	public ReviewMessageConsumer(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}
	
	@RabbitListener(queues = "companyRatingQueue")
	public void consumeMessage(ReviewMessage reviewMessage) {
		companyService.updateCompanyRating(reviewMessage);
	}

}
