package com.job.jobportal.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.job.jobportal.model.User;
//import org.springframework.security.core.userdetails.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
