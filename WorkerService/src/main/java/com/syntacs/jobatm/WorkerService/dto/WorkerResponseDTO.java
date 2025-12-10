package com.syntacs.jobatm.WorkerService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;

import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;
import com.syntacs.jobatm.WorkerService.util.AddressDetailsObject;
import com.syntacs.jobatm.WorkerService.util.DocumentObject;
import com.syntacs.jobatm.WorkerService.util.EducationDetailsObject;
import com.syntacs.jobatm.WorkerService.util.JobCategory;
import com.syntacs.jobatm.WorkerService.util.Languages;
import com.syntacs.jobatm.WorkerService.util.RegistrationType;
import com.syntacs.jobatm.WorkerService.util.Skill;

import lombok.Data;

@Data
public class WorkerResponseDTO {

    private Long workerId;

    // Basic Details
    private String workerName;
    private LocalDate birthDate;
    private int age;

    // Profile image
    private String profilePhoto;

    // Communication details
    private String workerPhone;
    private String workerEmail;

    // Regional Info
    private AddressDetailsObject workerAddress;
    private String location;
    private int preferredTravelDistance;
    private Languages preferredLanguage;

    // Educational details
    private Set<EducationDetailsObject> educationDetails = new HashSet<>();

    // Documents
    private Set<DocumentObject> documents = new HashSet<>();

    // Verification + NFC Details
    private Boolean isVerified; // changed from primitive boolean to wrapper

    private NfcCardStatus nfcCardStatus;
    private String nfcCardId;

    // Career Info
    private String workCategory;
    private Set<Skill> skills = new HashSet<>();

    private Double minWageRange; // changed from primitive double to wrapper
    private Double maxWageRange; // changed from primitive double to wrapper

    private Double workAcceptanceRate; // changed from primitive double to wrapper
    private Double workCompletionRate; // changed from primitive double to wrapper

    private Integer trustScore; // changed from primitive int to wrapper
    private Integer workerLevel; // changed from primitive int to wrapper

    // Registration Source Info
    private RegistrationType createdBy;
    private String kioskIdOfRegistration;

    private RegistrationType updatedBy;
    private String kioskIdOfUpdation;

    private LocalDateTime createdAtTime;
    private LocalDateTime updatedAtTime;
}