package com.job.jobportal.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="job_apply")
public class JobApply {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	 @ManyToOne
	 @JoinColumn(name = "job_id", nullable = false)
	 private Job job;
	 
//	@Column(length = 100, nullable = false)
//	private String JobId;
	
	@Column(length = 100,nullable=false)
	private String Name;
	
	@Column(length = 100,nullable = false)
	private String Email;
	
	@Column(length = 100,nullable=false)
	private String qualification;
	
	@Column(length = 500,nullable=false)
    private String currentLocation;
	
	@Column(length = 10,nullable=false)
	private String mobileNumber;

	@Column(length = 500,nullable=false)
	private  String filename;
//    @Lob
//    @Column(columnDefinition = "BLOB")
//    private byte[] resume; // Field to store the file data


//	public byte[] getResume() {
//		return resume;
//	}
//
//	public void setResume(byte[] resumeData) {
//		this.resume = resumeData;
//	}

	@Column(length = 200, nullable=false)
	private String postedate;
	
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getPostedate() {
		return postedate;
	}

	public void setPostedate(String postedate) {
		this.postedate = postedate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
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

//	public String getJobId() {
//		return JobId;
//	}
//
//	public void setJobId(String jobId) {
//		JobId = jobId;
//	}
	
}
