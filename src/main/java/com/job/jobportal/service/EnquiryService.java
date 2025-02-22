package com.job.jobportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.jobportal.Repository.EnquiryRepo;

@Service
public class EnquiryService {
    
    @Autowired
    private EnquiryRepo enquiryRepository;

    public boolean existsById(Long id) {
        return enquiryRepository.existsById(id);
    }

    public void deleteById(Long id) {
        enquiryRepository.deleteById(id);
    }
}
