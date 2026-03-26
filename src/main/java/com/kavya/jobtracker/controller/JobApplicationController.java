package com.kavya.jobtracker.controller;

import com.kavya.jobtracker.model.JobApplication;
import com.kavya.jobtracker.model.ApplicationStatus;
import com.kavya.jobtracker.model.User;
import com.kavya.jobtracker.service.JobApplicationService;
import com.kavya.jobtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<JobApplication> createApplication(@RequestBody JobApplication application) {
        JobApplication saved = jobApplicationService.createApplication(application);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<JobApplication>> getApplications(
            @PathVariable Long userId,
            @RequestParam(required = false) ApplicationStatus status) {

        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<JobApplication> applications;
        if (status != null) {
            applications = jobApplicationService.getApplicationsByStatus(user.get(), status);
        } else {
            applications = jobApplicationService.getAllApplications(user.get());
        }
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateApplication(
            @PathVariable Long id,
            @RequestBody JobApplication updatedApplication) {

        Optional<JobApplication> existing = jobApplicationService.getApplicationById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        JobApplication application = existing.get();
        application.setCompany(updatedApplication.getCompany());
        application.setRole(updatedApplication.getRole());
        application.setStatus(updatedApplication.getStatus());
        application.setNotes(updatedApplication.getNotes());
        application.setAppliedDate(updatedApplication.getAppliedDate());

        return ResponseEntity.ok(jobApplicationService.updateApplication(application));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        Optional<JobApplication> existing = jobApplicationService.getApplicationById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        jobApplicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
