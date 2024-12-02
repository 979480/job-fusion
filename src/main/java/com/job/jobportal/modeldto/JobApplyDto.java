package com.job.jobportal.modeldto;

import org.springframework.web.multipart.MultipartFile;

public class JobApplyDto {

    private String Name;
    private String Email;
    private String qualification;
    private String currentLocation;
    private String mobileNumber;
    private MultipartFile resume;
    private String postedate;
    
	public String getPostedate() {
		return postedate;
	}
	public void setPostedate(String postedate) {
		this.postedate = postedate;
	}
	public MultipartFile getResume() {
		return resume;
	}
	public void setResume(MultipartFile resume) {
		this.resume = resume;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
    }
