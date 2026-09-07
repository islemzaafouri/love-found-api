package com.lovefound.love_found_api.adoption_application.services;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.lovefound.love_found_api.adoption_application.dto.ApplicationRequest;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationResponse;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationUpdateRequest;
import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;

public interface ApplicationService {

    // =========================================================
    // ADOPTER
    // =========================================================

    ApplicationResponse createApplication(
            ApplicationRequest request,
            Authentication authentication
    );

    List<ApplicationResponse> getMyApplications(
            Authentication authentication
    );

    ApplicationResponse updateMyApplication(
            Long applicationId,
            ApplicationUpdateRequest request,
            Authentication authentication
    );

    void cancelMyApplication(
            Long applicationId,
            Authentication authentication
    );


    // =========================================================
    // SHELTER
    // =========================================================

    List<ApplicationResponse> getApplicationsForMyPets(
            Authentication authentication
    );

    ApplicationResponse updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status,
            Authentication authentication
    );
}