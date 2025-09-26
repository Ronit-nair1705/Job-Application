package com.embarkx.jobms.job.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.embarkx.jobms.clients.CompanyClient;
import com.embarkx.jobms.clients.ReviewClient;
import com.embarkx.jobms.job.Job;
import com.embarkx.jobms.job.JobRepository;
import com.embarkx.jobms.job.JobService;
import com.embarkx.jobms.job.dto.JobDTO;
import com.embarkx.jobms.job.external.Company;
import com.embarkx.jobms.job.external.Review;
import com.embarkx.jobms.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;



@Service
public class JobServiceImpl implements JobService {
	
	JobRepository jobRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	private CompanyClient companyClient;
	private ReviewClient reviewClient;
	
	int attempt = 0;
	
	   public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
		this.jobRepository = jobRepository;
		this.companyClient= companyClient;
		this.reviewClient=reviewClient;
	}

	   //private List<Job> jobs = new ArrayList<>();
	   

	@Override
//	@CircuitBreaker(name = "companyBreaker" , fallbackMethod = "companyBreakerFallBack")
//	@Retry(name = "companyBreaker" , fallbackMethod = "companyBreakerFallBack")
	@RateLimiter(name = "companyBreaker" , fallbackMethod = "companyBreakerFallBack")
	public List<JobDTO> findAll() {
		System.out.println("Attempt :"+ ++attempt);
		List<Job> jobs=jobRepository.findAll();
		List<JobDTO> jobDTOs = new ArrayList<>();
		
		return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	public List<String> companyBreakerFallBack(Exception e){
		List<String> list = new ArrayList<>();
		list.add("Dummy");
		return list;
		}
	
	private JobDTO convertToDto(Job job) {
		Company company = companyClient.getCompany(job.getCompanyId());
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
		JobDTO jobDTO=JobMapper.mapToJobwithCompanyDTO(job, company,reviews);
		return jobDTO;
			}

	@Override
	public void createJob(Job job) {
		job.setId(null);
		
		jobRepository.save(job);
		
	}

	@Override
	public JobDTO getJobById(Long id) {
	    return jobRepository.findById(id)
	        .map(this::convertToDto)
	        .orElse(null);
	}


	@Override
	public boolean deleteJobById(Long id) {
	try{ 
		jobRepository.deleteById(id);
	return true;
	}
	catch(Exception e) {
		return false;
	}
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
			if(jobOptional.isPresent()) {
				Job job = jobOptional.get();
				job.setTitle(updatedJob.getTitle());
				job.setDescription(updatedJob.getDescription());
				job.setMinSalary(updatedJob.getMinSalary());
				job.setMaxSalary(updatedJob.getMaxSalary());
				job.setLocation(updatedJob.getLocation());
				jobRepository.save(job);
				return true;
			}
		
		return false;
	}

}
