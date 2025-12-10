package com.syntacs.jobatm.WorkerService.dto;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Enumerated;
import jakarta.annotation.Nullable;
import jakarta.persistence.EnumType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

import com.syntacs.jobatm.WorkerService.util.AddressDetailsObject;
import com.syntacs.jobatm.WorkerService.util.DocumentObject;
import com.syntacs.jobatm.WorkerService.util.EducationDetailsObject;
import com.syntacs.jobatm.WorkerService.util.JobCategory;
import com.syntacs.jobatm.WorkerService.util.Languages;
import com.syntacs.jobatm.WorkerService.util.RegistrationType;
import com.syntacs.jobatm.WorkerService.util.Skill;

@Data
public class WorkerRegistrationDTO {

    // Basic Details
    @NotBlank
    @Size(max = 255)
    private String workerName;

    @NotNull
    private LocalDate birthDate;

    // Profile photo upload
    @Nullable
    private MultipartFile profilePhoto;

    // Communication details
    @NotBlank
    @Size(max = 20)
    private String workerPhone;

    @Email
    @Size(max = 255)
    private String workerEmail;

    @NotBlank
    @Size(max = 100)
    private String workerPassword;

    // Regional Information
    private AddressDetailsObject workerAddress;

    @Nullable
    private Double latitude;
    @Nullable
    private Double longitude;

    @Min(0)
    private Integer preferredTravelDistance; // unit in kilometres

    @Enumerated(EnumType.STRING)
    private Languages preferredLanguage;

    // Educational details
    private Set<EducationDetailsObject> workerEducationDetails = new HashSet<>();

    // Documents
    private Set<DocumentObject> workerDocuments = new HashSet<>();

    // @NotBlank
    // @Size(max = 12)
    // private String aadhaarNumber;

    // @Size(max = 10)
    // private String panNumber;

    // @Size(max = 20)
    // private String voterCardNumber;

    // Career Info
    @NotBlank(message = "Work category cannot be blank")
    @Size(max = 25)
    private String workCategory;

    private Set<Skill> skills = new HashSet<>();

    @Nullable
    private Double minWageRange;
    @Nullable
    private Double maxWageRange;

    @NotNull
    private RegistrationType createdBy; // Registration Source (SELF / KIOSK)
    @Nullable
    private String kioskIdOfRegistration;

    @NotNull
    private RegistrationType updatedBy; // Registration Source (SELF / KIOSK)
    @Nullable
    private String kioskIdOfUpdation;

    @PrePersist
    @PreUpdate
    public void validateWageRange() {
        if (minWageRange > 0 && maxWageRange > 0 && minWageRange >= maxWageRange) {
            throw new IllegalArgumentException("min_wage_range must be less than max_wage_range");
        }
    }

    @Override
    public String toString() {
        return "WorkerRegistrationDTO {" +
                ", Name = '" + workerName + '\'' +
                ", Birth date = " + birthDate +
                // ", Age (years) = " + getAge() +
                ", Mobile number = '" + workerPhone + '\'' +
                ", Email = '" + workerEmail + '\'' +
                ", Address details = '" + workerAddress + '\'' +
                ", Location cordinates = (" + latitude + ", " + longitude + ")" +
                ", Preferred travel distance = " + preferredTravelDistance +
                ", Preferred language = " + preferredLanguage +
                ", Educational details = " + workerEducationDetails +
                ", Uploaded identity proofs = " + workerDocuments +
                ", Work category = '" + workCategory + '\'' +
                ", Wage range = (" + minWageRange + " - " + maxWageRange + ")" +
                ", Created by = " + createdBy +
                ", Kiosk id of Registration = " + kioskIdOfRegistration +
                ", Updated by = " + updatedBy +
                ", Kiosk id of updation = " + kioskIdOfUpdation +
                '}';
    }
}