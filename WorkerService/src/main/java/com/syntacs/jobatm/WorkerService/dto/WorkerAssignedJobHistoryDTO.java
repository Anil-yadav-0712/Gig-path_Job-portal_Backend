package com.syntacs.jobatm.WorkerService.dto;

import lombok.Data;

import java.time.LocalDateTime;

import com.syntacs.jobatm.WorkerService.util.AttendanceStatus;
import com.syntacs.jobatm.WorkerService.util.CompletionStatus;

@Data
public class WorkerAssignedJobHistoryDTO {
    private long assignedHistoryId;
    private long jobId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CompletionStatus completionStatus; // ASSIGNED, IN_PROGRESS, COMPLETED
    private double employerRating;
    private String feedback;
    private double wagePaid;
    private AttendanceStatus attendanceStatus; // PRESENT -ONTIME, PRESENT -LATE, ABSENT -NEVER ARRIVED
    private LocalDateTime createdAtTime;
    private LocalDateTime updatedAtTime;
}
