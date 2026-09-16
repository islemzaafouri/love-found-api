package com.lovefound.love_found_api.adoption_application.services;

import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.lovefound.love_found_api.adoption_application.dto.ApplicationRequest;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationResponse;
import com.lovefound.love_found_api.adoption_application.dto.ApplicationUpdateRequest;
import com.lovefound.love_found_api.adoption_application.models.entities.Application;
import com.lovefound.love_found_api.adoption_application.models.enums.ApplicationStatus;
import com.lovefound.love_found_api.adoption_application.repository.ApplicationRepo;
import com.lovefound.love_found_api.auth.model.entities.User;
import com.lovefound.love_found_api.auth.repository.UserRepo;
import com.lovefound.love_found_api.core.exceptions.ResourceNotFoundException;
import com.lovefound.love_found_api.core.exceptions.UnauthorizedException;
import com.lovefound.love_found_api.pet.PetMapper;
import com.lovefound.love_found_api.pet.dto.PetResponse;
import com.lovefound.love_found_api.pet.model.entity.Pet;
import com.lovefound.love_found_api.pet.model.enums.PetStatus;
import com.lovefound.love_found_api.pet.repository.PetRepo;
import com.lovefound.love_found_api.profiles.ProfileMapper;
import com.lovefound.love_found_api.profiles.Repos.AdopterProfileRepo;
import com.lovefound.love_found_api.profiles.Repos.ShelterProfileRepo;
import com.lovefound.love_found_api.profiles.dto.AdopterProfileResponse;
import com.lovefound.love_found_api.profiles.entities.AdopterProfile;
import com.lovefound.love_found_api.profiles.entities.ShelterProfile;

@Service
public class ApplicationServiceImpl implements ApplicationService{
    private final ApplicationRepo applicationRepository;
    private final PetRepo petRepository;
    private final AdopterProfileRepo adopterProfileRepository;
    private final ShelterProfileRepo shelterProfileRepository;
    private final UserRepo userRepository;
    private final ProfileMapper profileMapper;
    private final PetMapper petMapper;
    
    public ApplicationServiceImpl(ApplicationRepo applicationRepository, PetRepo petRepository, AdopterProfileRepo adopterProfileRepository, ShelterProfileRepo shelterProfileRepository, UserRepo userRepository, ProfileMapper profileMapper, PetMapper petMapper) {
        this.applicationRepository = applicationRepository;
        this.petRepository = petRepository;
        this.adopterProfileRepository = adopterProfileRepository;
        this.shelterProfileRepository = shelterProfileRepository;
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
        this.petMapper = petMapper;

    }

    //ADOPTER METHODS
    @Override
    public ApplicationResponse createApplication(ApplicationRequest request,Authentication authentication) {
        // Get the authenticated adopter
        AdopterProfile adopter =getAuthenticatedAdopter(authentication);
        //find the pet by id
        Pet pet = petRepository.findById(request.getPetId()).orElseThrow(() -> new ResourceNotFoundException("Pet not found"));
        //the pet must be available for adoption
        if (pet.getStatus() != PetStatus.AVAILABLE) {
            throw new IllegalStateException("Pet is not available for adoption");
        }
        //prevent duplicate applications for the same pet by the same adopter
        if (applicationRepository.existsByAdopterIdAndPetId(adopter.getId(), pet.getId())) {
            throw new IllegalStateException("You have already applied for this pet");
        }
        //create the application
        Application application = new Application();
        application.setAdopter(adopter);
        application.setPet(pet);
        application.setMotivation(request.getMotivation());
        Application savedApplication = applicationRepository.save(application);
        return mapToResponse(savedApplication);

    }
    @Override
    public List<ApplicationResponse> getMyApplications(Authentication authentication) {
        AdopterProfile adopter = getAuthenticatedAdopter(authentication);
        List<Application> applications = applicationRepository.findByAdopterId(adopter.getId());
        return applications.stream().map(this::mapToResponse).toList();
    }

    @Override
    public ApplicationResponse updateMyApplication(Long applicationId, ApplicationUpdateRequest request, Authentication authentication)
     {  Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        AdopterProfile adopter = getAuthenticatedAdopter(authentication);
        if (!application.getAdopter().getId().equals(adopter.getId())) {
            throw new AccessDeniedException("You are not authorized to update this application");
        }
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("You can only update pending applications");
        }
        application.setMotivation(request.getMotivation());
        Application updatedApplication = applicationRepository.save(application);
        return mapToResponse(updatedApplication);
    }

    @Override
    public void cancelMyApplication(Long applicationId, Authentication authentication) {
        AdopterProfile adopter = getAuthenticatedAdopter(authentication);
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        String currentUserEmail = authentication.getName(); // Or user ID depending on JWT setup
    
        // Debug point: Print both to your terminal to see why they don't match!
        // System.out.println("Owner: " + application.getAdopter().getUser().getEmail());
        // System.out.println("Current: " + currentUserEmail);

        if (!application.getAdopter().getId().equals(adopter.getId())) {
            throw new AccessDeniedException("You are not authorized to cancel this application");
        }
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("You can only cancel pending applications");
        }
        application.setStatus(ApplicationStatus.CANCELLED);
        applicationRepository.save(application);
    }

    //SHELTER METHODS
    @Override
    public List<ApplicationResponse> getApplicationsForMyPets(Authentication authentication) {
        Long shelterId = getAuthenticatedShelter(authentication).getId();
        List<Application> applications = applicationRepository.findByPetShelterIdAndStatusNot(shelterId,ApplicationStatus.CANCELLED);
        return applications.stream().map(this::mapToResponse).toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatus status, Authentication authentication) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        if (!application.getPet().getShelter().getId().equals(getAuthenticatedShelter(authentication).getId())) {
            throw new AccessDeniedException("You are not authorized to update this application");
        }  
        if (application.getStatus() != ApplicationStatus.PENDING) {
                throw new IllegalStateException("You can only update pending applications");
            }
        if(status!=ApplicationStatus.ACCEPTED && status!=ApplicationStatus.REJECTED){
            throw new IllegalArgumentException("Invalid status. Only APPROVED or REJECTED are allowed.");
        }
        if (status == ApplicationStatus.ACCEPTED) {
            // Check if the pet is still available for adoption
            if (application.getPet().getStatus() != PetStatus.AVAILABLE) {
                throw new IllegalStateException("Pet is no longer available for adoption");
            }
            // Update the pet's status to adopted
            Pet pet = application.getPet();
            pet.setStatus(PetStatus.ADOPTED);
            petRepository.save(pet);
        }
        application.setStatus(status);
        Application updatedApplication = applicationRepository.save(application);
        return mapToResponse(updatedApplication);
    }

    @Override
    public ApplicationResponse getApplicationById(Long applicationId, Authentication authentication) {
    Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    User authenticatedUser = getAuthenticatedUser(authentication);

    boolean isAdopterOwner = application.getAdopter().getUser().getId().equals(authenticatedUser.getId());
    boolean isShelterOwner = application.getPet().getShelter().getUser().getId().equals(authenticatedUser.getId());

    if (!isAdopterOwner && !isShelterOwner) {
        throw new AccessDeniedException("You are not authorized to view this application");
    }
    return mapToResponse(application);
}

    // Helper methods
    private AdopterProfile getAuthenticatedAdopter(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        AdopterProfile adopter = adopterProfileRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Adopter profile not found"));
        return adopter; 
    }
    private ShelterProfile getAuthenticatedShelter(Authentication authentication) {
        User user = getAuthenticatedUser(authentication);
        ShelterProfile shelter = shelterProfileRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Shelter profile not found"));
        return shelter; 
    }
    private User getAuthenticatedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not authenticated");
        }
        String email = authentication.getName();

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("Authenticated user not found.");
        }

        return user;
    }

    private ApplicationResponse mapToResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();
        response.setId(application.getId());
        AdopterProfileResponse adopter=profileMapper.toAdopterResponse(application.getAdopter());
        response.setAdopter(adopter);
        PetResponse pet=petMapper.toResponse(application.getPet());
        response.setPet(pet);
        response.setMotivation(application.getMotivation());
        response.setStatus(application.getStatus());
        return response;
    }









}
