package com.job.jobportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.jobportal.model.JobApply;

public interface JobApplyRepository extends JpaRepository<JobApply, Long>{

}
