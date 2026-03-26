package com.kavya.jobtracker.repository;

import com.kavya.jobtracker.model.JobApplication;
import com.kavya.jobtracker.model.ApplicationStatus;
import com.kavya.jobtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    List<JobApplication> findByUser(User user);
    List<JobApplication> findByUserAndStatus(User user, ApplicationStatus status);
}
