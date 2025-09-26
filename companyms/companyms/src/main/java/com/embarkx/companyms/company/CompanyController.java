package com.embarkx.companyms.company;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		
		this.companyService = companyService;
	}
	
	@RequestMapping
	public ResponseEntity<List<Company>> getAllCompanies(){
		return new ResponseEntity<>(companyService.getAllCompanies(),HttpStatus.OK);
	}
	
	 @RequestMapping(method=RequestMethod.PUT,value="/{id}")
	public ResponseEntity<String> updateCompany(@PathVariable Long id,@RequestBody Company company){
		companyService.updateCompany(company,id);
		return new ResponseEntity<>("Company Updated Sucessfully",HttpStatus.OK);
	} 
	 
	 @RequestMapping(method=RequestMethod.POST)
	 public ResponseEntity<String> addCompany(@RequestBody Company company){
		 companyService.createCompany(company);
		 return new ResponseEntity<>("Company Added Sucessfully",HttpStatus.CREATED);
	 }
	 
	 @RequestMapping(method=RequestMethod.DELETE,value="/{id}")
	 public ResponseEntity<String> deleteCompany(@PathVariable Long id){
		 boolean isDeleted = companyService.deleteCompanyById(id);
		 if(isDeleted) {
			 return new ResponseEntity<>("Company Deleted Sucessfully",HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>("Company Not Found",HttpStatus.NOT_FOUND);
		 }
	 }
	 
	 @RequestMapping(method=RequestMethod.GET,value="/{id}")
	 public ResponseEntity<Company> getCompany(@PathVariable Long id){
		 Company company = companyService.getCompanyById(id);
		 
		 if(company != null) {
			 return new ResponseEntity<>(company,HttpStatus.OK);
		 }
		 else {
			 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		 }
	 }

}
