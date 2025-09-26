package com.embarkx.companyms.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REVIEW-SERVICE",url = "${review-service.url}")
public interface ReviewClient {
	
	 @RequestMapping(method=RequestMethod.GET,value="/review/averageRating")
	 Double getAverageRatingForCompany(@RequestParam("companyId") Long companyId);

}
