package com.syntacs.jobatm.WorkerService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntacs.jobatm.WorkerService.util.DocumentVerificationStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "worker_documents")
public class WorkerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @JsonIgnore // prevent infinite recursion
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String documentType;

    @Size(max = 100)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentVerificationStatus verificationStatus = DocumentVerificationStatus.PENDING;

    private Long verifiedBy; // Admin ID (optional)

    private LocalDateTime verifiedAt;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    // Constructors
    public WorkerDocument() {}

    public WorkerDocument(Worker worker, String documentType, String documentNumber) {
        this.worker = worker;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkerDocument)) return false;

        WorkerDocument that = (WorkerDocument) o;
        if (this.documentId == null || that.documentId == null) return false;

        return this.documentId.equals(that.documentId);
    }

    @Override
    public int hashCode() {
        return documentId != null ? documentId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerDocument {" +
                "documentId=" + documentId +
                ", workerId=" + (worker != null ? worker.getWorkerId() : null) +
                ", documentType='" + documentType + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", verificationStatus=" + verificationStatus +
                ", verifiedBy=" + verifiedBy +
                ", verifiedAt=" + verifiedAt +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}