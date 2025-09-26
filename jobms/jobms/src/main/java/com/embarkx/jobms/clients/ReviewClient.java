package com.embarkx.jobms.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;

@FeignClient(name="REVIEW-SERVICE",url = "${review-service.url}")
public interface ReviewClient {
	@GetMapping("/reviews")
	List<Review> getReviews(@RequestParam("companyId")Long companyId);


}
