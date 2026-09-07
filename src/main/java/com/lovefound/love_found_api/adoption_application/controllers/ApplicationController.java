package com.lovefound.love_found_api.adoption_application.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lovefound.love_found_api.adoption_application.dto.ApplicationRequest;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationResponse;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationStatusUpdateRequest;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationUpdateRequest;
import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;
import com.lovefound.love_found_api.adoption_application.services.ApplicationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin(origins = "http://localhost:4200")
public class ApplicationController {
    private final ApplicationService applicationService;
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    //ADOPTER ENDPOINTS
    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@RequestBody @Valid ApplicationRequest request, Authentication authentication) {
        ApplicationResponse response = applicationService.createApplication(request, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/my-applications")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(Authentication authentication) {
        List<ApplicationResponse> applications = applicationService.getMyApplications(authentication);
        return ResponseEntity.ok(applications);
    }
    @PutMapping("/{applicationId}")
    public ResponseEntity<ApplicationResponse>updateMyApplication(@PathVariable Long applicationId, @RequestBody @Valid ApplicationUpdateRequest request, Authentication authentication) {
        ApplicationResponse response = applicationService.updateMyApplication(applicationId, request, authentication);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{applicationId}/cancel")
    public ResponseEntity<Void> cancelMyApplication(@PathVariable Long applicationId, Authentication authentication) {
        applicationService.cancelMyApplication(applicationId, authentication);
        return ResponseEntity.noContent().build();
    }
    //SHELTER ENDPOINTS
    @GetMapping("/shelter-applications")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsForMyPets(Authentication authentication) {
        List<ApplicationResponse> applications = applicationService.getApplicationsForMyPets(authentication);
        return ResponseEntity.ok(applications);
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(@PathVariable Long applicationId, @RequestBody ApplicationStatusUpdateRequest request,Authentication authentication) {
        ApplicationResponse response = applicationService.updateApplicationStatus(applicationId,request.getStatus() , authentication);
        return ResponseEntity.ok(response);
    }   

    
}
