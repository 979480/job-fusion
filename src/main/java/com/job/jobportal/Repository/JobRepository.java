package com.job.jobportal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.jobportal.model.Job;

public interface JobRepository extends JpaRepository<Job,Long>{

//	List<Job> findByTitleContainingIgnoreCase(String title);
//
//	List<Job> findByLocationContainingIgnoreCase(String location);

	List<Job> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(String keyword, String keyword2);

}
