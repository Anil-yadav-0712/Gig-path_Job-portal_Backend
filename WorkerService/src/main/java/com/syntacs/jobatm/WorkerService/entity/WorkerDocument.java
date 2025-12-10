package com.syntacs.jobatm.WorkerService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntacs.jobatm.WorkerService.util.VerificationStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "worker_documents")
public class WorkerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String documentName;

    @Size(max = 100)
    @Column(nullable = true)
    private String documentIssueNumber;

    @Column(nullable = false)
    private String issuingAuthority;

    @Column(nullable = true)
    private LocalDate issueDate;

    @Column(nullable = true)
    private LocalDate documentValidity;

    @Lob
    @Column(name = "file_data", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus documentVerificationStatus = VerificationStatus.PENDING;

    @Column(nullable = true)
    private LocalDateTime verifiedAt;

    // ⚠ Changed from long → Long (null-safe)
    @Column(nullable = true)
    private Long verifiedBy;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String rejectionReason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAtTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAtTime;

    @Column(nullable = true)
    private LocalDateTime deletedAtTime;

    @Column(nullable = true)
    private String deletionReason;

    // Constructors
    public WorkerDocument() {
    }

    public WorkerDocument(Worker worker, String documentName, String issuingAuthority,
            LocalDate issueDate, LocalDate documentValidity) {
        this.worker = worker;
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = VerificationStatus.PENDING;
    }

    public WorkerDocument(Worker worker, String documentName, String documentIssueNumber,
            String issuingAuthority, LocalDate issueDate, LocalDate documentValidity) {
        this.worker = worker;
        this.documentName = documentName;
        this.documentIssueNumber = documentIssueNumber;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = VerificationStatus.PENDING;
    }

    public WorkerDocument(Worker worker, String documentName, String issuingAuthority,
            LocalDate issueDate, LocalDate documentValidity,
            VerificationStatus documentVerificationStatus, String rejectionReason) {
        this.worker = worker;
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = documentVerificationStatus;
        this.rejectionReason = rejectionReason;
    }

    public WorkerDocument(Worker worker, String documentName, String issuingAuthority,
            LocalDate issueDate, LocalDate documentValidity,
            VerificationStatus documentVerificationStatus,
            String rejectionReason, String deletionReason) {
        this.worker = worker;
        this.documentName = documentName;
        this.issuingAuthority = issuingAuthority;
        this.issueDate = issueDate;
        this.documentValidity = documentValidity;
        this.documentVerificationStatus = documentVerificationStatus;
        this.rejectionReason = rejectionReason;
        this.deletionReason = deletionReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof WorkerDocument))
            return false;
        WorkerDocument that = (WorkerDocument) o;

        if (this.documentId == null || that.documentId == null)
            return false;

        return this.documentId.equals(that.documentId);
    }

    @Override
    public int hashCode() {
        return documentId != null ? documentId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerDocument {" +
                "Document id =" + documentId +
                ", Worker id =" + (worker != null ? worker.getWorkerId() : null) +
                ", Worker name =" + (worker != null ? worker.getWorkerName() : null) +
                ", Document name ='" + documentName + '\'' +
                ", Document number ='" + documentIssueNumber + '\'' +
                ", Issuing authority ='" + issuingAuthority + '\'' +
                ", Issue date =" + issueDate + '\'' +
                ", Validity ='" + documentValidity + '\'' +
                ", Verification Status =" + documentVerificationStatus +
                ", Verified/Rejected by =" + verifiedBy +
                ", Verified/Rejected at time =" + verifiedAt +
                ", Rejection Reason ='" + rejectionReason + '\'' +
                ", Uploaded at =" + createdAtTime +
                ", Updated at =" + updatedAtTime +
                '}';
    }
}