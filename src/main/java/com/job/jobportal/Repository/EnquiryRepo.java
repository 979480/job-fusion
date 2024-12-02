package com.job.jobportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.job.jobportal.model.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Long>{

	Enquiry getById(Long Id);

	void delete(Enquiry res);

}
