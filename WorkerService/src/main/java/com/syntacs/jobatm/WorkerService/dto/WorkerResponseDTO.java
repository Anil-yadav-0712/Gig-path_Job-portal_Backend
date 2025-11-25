package com.syntacs.jobatm.WorkerService.dto;

import java.util.Set;

import com.syntacs.jobatm.WorkerService.util.DocumentVerificationStatus;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;
import com.syntacs.jobatm.WorkerService.util.PreferredLanguage;
import com.syntacs.jobatm.WorkerService.util.RegistrationSource;

import lombok.Data;

@Data
public class WorkerResponseDTO {

    private Long workerId;

    // Basic Details
    private String workerName;
    private int age;
    private String workerEmail;
    private String workerPhone;

    // Documents
    private String aadhaarNumber;
    private String panNumber;
    private String voterCardNumber;

    private Set<DocumentDTO> documents;

    // Regional Info
    private String location;
    private Integer preferredTravelDistance;
    private PreferredLanguage preferredLanguage;

    // Career Info
    private String workCategory;
    private Double minWageRange;
    private Double maxWageRange;
    private Double gigLevel;

    // Skills
    private Set<String> skills;

    // Verification Info
    private Boolean isVerified;
    private NfcCardStatus nfcCardStatus;

    // Registration Source Info
    private RegistrationSource createdBy;
    private RegistrationSource updatedBy;
    private String kioskIdOfRegistration;

    // Job Stats
    private Double jobAcceptanceRate;
    private Double jobCompletionRate;

    // Inner DTO for document info
    public static class DocumentDTO {
        private String documentType;
        private String documentNumber;
        private DocumentVerificationStatus verificationStatus;

    }
}