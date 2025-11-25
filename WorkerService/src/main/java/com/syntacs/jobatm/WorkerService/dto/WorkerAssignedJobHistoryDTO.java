package com.syntacs.jobatm.WorkerService.dto;

import lombok.Data;

import java.time.LocalDateTime;

import com.syntacs.jobatm.WorkerService.util.AttendanceStatus;
import com.syntacs.jobatm.WorkerService.util.CompletionStatus;

@Data
public class WorkerAssignedJobHistoryDTO {
    private Long assignedHistoryId;
    private Long jobId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CompletionStatus completionStatus; // ASSIGNED, IN_PROGRESS, COMPLETED
    private Double employerRating;
    private String feedback;
    private Double wagePaid;
    private AttendanceStatus attendanceStatus; // PRESENT -ONTIME, PRESENT -LATE, ABSENT -NEVER ARRIVED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
