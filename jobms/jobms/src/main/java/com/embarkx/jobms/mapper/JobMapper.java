package com.embarkx.jobms.mapper;

import java.util.List;

import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;

public class JobMapper {
	public static JobDTO mapToJobwithCompanyDTO(Job job,Company company,List<Review> reviews) {
		
		JobDTO jobDTO = new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);
		
		
		return jobDTO;
		
	}

}
