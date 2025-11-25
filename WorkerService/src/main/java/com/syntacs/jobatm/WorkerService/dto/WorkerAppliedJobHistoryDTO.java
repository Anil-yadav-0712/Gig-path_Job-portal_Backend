package com.syntacs.jobatm.WorkerService.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WorkerAppliedJobHistoryDTO {
    private Long appliedHistoryId;
    private Long jobId;
    
    private String applicationStatus;  // PENDING, APPROVED, REJECTED, etc.
    private String rejectionReason;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}
