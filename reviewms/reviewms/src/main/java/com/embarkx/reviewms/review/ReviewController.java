package com.embarkx.reviewms.review;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.embarkx.reviewms.review.messaging.ReviewMessageProducer;



@RestController
@RequestMapping("/reviews")
public class ReviewController {
	private ReviewService reviewService;
	private ReviewMessageProducer reviewMessageProducer;

	public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
		this.reviewService = reviewService;
		this.reviewMessageProducer=reviewMessageProducer;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
		return new ResponseEntity<>(reviewService.getAllReviews(companyId),HttpStatus.OK);
		
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> addReview(@RequestParam Long companyId,@RequestBody Review review){
		
		boolean isReviewSaved=reviewService.addReview(companyId, review);
		if(isReviewSaved) {
			reviewMessageProducer.sendMessage(review);
		return new ResponseEntity<>("Review Added Sucessfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("Review Not Saved",HttpStatus.NOT_FOUND);
		}
	}
	
	 @RequestMapping(method=RequestMethod.GET,value="/{reviewId}")
	    public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
	    	return new ResponseEntity<>(reviewService.getReview(reviewId),HttpStatus.OK);
	    } 
	 
	 
	 @RequestMapping(method=RequestMethod.PUT,value="{reviewId}")
	 public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody Review review){
		 
		 boolean isReviewUpdated = reviewService.updateReview(reviewId, review);
		 if(isReviewUpdated) {
			 
			 return new ResponseEntity<>("Review Updated Sucessfully",HttpStatus.OK);
		 }else {
		 return new ResponseEntity<>("Review Not Found",HttpStatus.ACCEPTED.NOT_FOUND);
		 }
	 }
	 
	 @RequestMapping(method=RequestMethod.DELETE,value="{reviewId}")
	 public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
		 
		 boolean isReviewDeleted = reviewService.deleteReview(reviewId);
		 if(isReviewDeleted) {
			 return new ResponseEntity<>("Review Deleted Sucessfully",HttpStatus.OK);
		 }else {
		 return new ResponseEntity<>("Review Not Found",HttpStatus.ACCEPTED.NOT_FOUND);
		 }
	 }
	 
	 @RequestMapping(method=RequestMethod.GET,value="/averageRating")
	 public Double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviewList = reviewService.getAllReviews(companyId);
		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
		 
		 
	 }
	 
}
