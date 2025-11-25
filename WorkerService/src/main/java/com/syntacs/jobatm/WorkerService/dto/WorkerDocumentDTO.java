package com.syntacs.jobatm.WorkerService.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkerDocumentDTO {
    private Long documentId;
    private String documentType;
    private String documentNumber;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String verificationStatus; // PENDING, VERIFIED, REJECTED
    private LocalDateTime verifiedAt;
    private Long verifiedBy;
    private String rejectionReason;
}