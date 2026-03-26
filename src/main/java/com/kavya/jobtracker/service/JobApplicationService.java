package com.kavya.jobtracker.service;

import com.kavya.jobtracker.model.JobApplication;
import com.kavya.jobtracker.model.ApplicationStatus;
import com.kavya.jobtracker.model.User;
import com.kavya.jobtracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    public JobApplication createApplication(JobApplication application) {
        return jobApplicationRepository.save(application);
    }

    public List<JobApplication> getAllApplications(User user) {
        return jobApplicationRepository.findByUser(user);
    }

    public List<JobApplication> getApplicationsByStatus(User user, ApplicationStatus status) {
        return jobApplicationRepository.findByUserAndStatus(user, status);
    }

    public Optional<JobApplication> getApplicationById(Long id) {
        return jobApplicationRepository.findById(id);
    }

    public JobApplication updateApplication(JobApplication application) {
        return jobApplicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}
