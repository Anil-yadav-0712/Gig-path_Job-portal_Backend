package com.syntacs.jobatm.WorkerService.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import com.syntacs.jobatm.WorkerService.util.PreferredLanguage;
import com.syntacs.jobatm.WorkerService.util.RegistrationSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
public class WorkerRegistrationDTO {

    // Basic Details
    @NotBlank
    @Size(max = 255)
    private String workerName;

    @NotNull
    private LocalDate birthDate;

    @Email
    @Size(max = 255)
    private String workerEmail;

    @NotBlank
    @Size(max = 20)
    private String workerPhone;

    @NotBlank
    @Size(max = 100)
    private String workerPassword;


    // Documents
    @NotBlank
    @Size(max = 12)
    private String aadhaarNumber;

    @Size(max = 10)
    private String panNumber;

    @Size(max = 20)
    private String voterCardNumber;

    // Uploaded documents
    private List<DocumentUploadDTO> documents;


    //Registration info
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    private RegistrationSource createdBy;       // Registration Source (SELF / KIOSK)

    @NotNull
    private RegistrationSource updatedBy;       // Registration Source (SELF / KIOSK)

    private String kioskIdOfRegistration;


    // Regional Info
    @NotBlank
    @Size(max = 255)
    private String location;

    private Integer preferredTravelDistance;

    private PreferredLanguage preferredLanguage;


    // Career Info
    @NotBlank
    @Size(max = 100)
    private String workCategory;

    private Double minWageRange;
    private Double maxWageRange;
    private Double gigLevel;

    private Set<String> skills;


    // Inner DTO for document upload
    public static class DocumentUploadDTO {
        @NotBlank
        @Size(max = 50)
        private String documentType;

        @NotNull
        private MultipartFile file; // or base64 String if JSON only
    }
}