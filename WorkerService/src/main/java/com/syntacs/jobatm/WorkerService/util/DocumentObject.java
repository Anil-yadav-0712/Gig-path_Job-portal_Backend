package com.syntacs.jobatm.WorkerService.util;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentObject {

    @NotBlank
    @Size(max = 50)
    private String documentName;

    @Size(max = 100)
    private String documentIssueNumber;

    @NotBlank
    private String issuingAuthority;

    @NotNull
    private LocalDate issueDate;

    @Nullable
    private LocalDate documentValidity;

    @Nullable
    private MultipartFile file;

    @NotNull
    private VerificationStatus documentVerificationStatus;

    @Nullable
    private LocalDateTime verifiedAt;

    @Nullable
    private Long verifiedBy; // changed from long → Long

    @NotBlank
    @Column(nullable = true)
    private String rejectionReason;

    @CreationTimestamp
    private LocalDateTime createdAtTime;

    @UpdateTimestamp
    private LocalDateTime updatedAtTime;

    @Nullable
    private LocalDateTime deletedAtTime;

    @Nullable
    private String deletionReason;

    // Constructors
    public DocumentObject() {
    }

    public DocumentObject(String documentName, String issuingAuthority, LocalDate issueDate,
            LocalDate documentValidity) {
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = VerificationStatus.PENDING;
    }

    public DocumentObject(String documentName, String documentIssueNumber, String issuingAuthority,
            LocalDate issueDate, LocalDate documentValidity) {
        this.documentName = documentName;
        this.documentIssueNumber = documentIssueNumber;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = VerificationStatus.PENDING;
    }

    public DocumentObject(String documentName, String issuingAuthority, LocalDate issueDate,
            LocalDate documentValidity, VerificationStatus documentVerificationStatus, LocalDateTime verifiedAt,
            Long verifiedBy) {
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = documentVerificationStatus;
        this.verifiedAt = verifiedAt;
        this.verifiedBy = verifiedBy;
    }

    public DocumentObject(String documentName, String issuingAuthority, LocalDate issueDate,
            LocalDate documentValidity, VerificationStatus documentVerificationStatus, LocalDateTime verifiedAt,
            String rejectionReason) {
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = documentVerificationStatus;
        this.verifiedAt = verifiedAt;
        this.rejectionReason = rejectionReason;
    }

    public DocumentObject(String documentName, String issuingAuthority, LocalDate issueDate,
            LocalDate documentValidity, VerificationStatus documentVerificationStatus, LocalDateTime verifiedAt,
            String rejectionReason, String deletionReason) {
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = documentVerificationStatus;
        this.verifiedAt = verifiedAt;
        this.rejectionReason = rejectionReason;
        this.deletionReason = deletionReason;
    }

    @Override
    public String toString() {
        return "WorkerDocument {" +
                ", Document name ='" + documentName + '\'' +
                ", Document number ='" + documentIssueNumber + '\'' +
                ", Issuing authority ='" + issuingAuthority + '\'' +
                ", Issue date =" + issueDate + '\'' +
                ", Validity/Expiry date =" + documentValidity + '\'' +
                ", Verification Status =" + documentVerificationStatus +
                ", Verified/Rejected by =" + verifiedBy +
                ", Verified/Rejected at time =" + verifiedAt +
                ", Rejection Reason ='" + rejectionReason + '\'' +
                ", Uploaded at =" + createdAtTime +
                ", Updated at =" + updatedAtTime +
                '}';
    }

    public void setFile(String encodeToString) {
        throw new UnsupportedOperationException("Unimplemented method 'setFile'");
    }
}