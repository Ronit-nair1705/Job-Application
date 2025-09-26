package com.embarkx.jobms.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.embarkx.jobms.job.dto.JobDTO;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
   private JobService jobService;
   
   public JobController(JobService jobService) {
		this.jobService = jobService;
	}
    
    @RequestMapping()
	public ResponseEntity<List<JobDTO>> findAll(){
		return ResponseEntity.ok(jobService.findAll());
	}
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> createJob(@RequestBody Job job) {
    	jobService.createJob(job);
    	
    	return new ResponseEntity<>("Job Added Sucessfully",HttpStatus.CREATED);
    	
    }
    
    @RequestMapping("{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
    	JobDTO jobDTO = jobService.getJobById(id);
    	if(jobDTO != null) {
    		return new ResponseEntity<>(jobDTO,HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    	
    }
    
    @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id){
    	boolean deleted = jobService.deleteJobById(id);
    	if(deleted)
    		return new ResponseEntity<>("Job Deleted Successfully",HttpStatus.OK);
    		
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @RequestMapping(method=RequestMethod.PUT,value="/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedJob){
    	
    	boolean updated= jobService.updateJob(id,updatedJob);
    	if(updated)
    		return new ResponseEntity<>("Job Updated Successfully",HttpStatus.OK);
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	
    }
    
   

	
}
