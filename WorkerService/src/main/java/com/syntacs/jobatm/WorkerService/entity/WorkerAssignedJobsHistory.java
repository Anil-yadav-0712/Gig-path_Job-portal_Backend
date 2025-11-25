package com.syntacs.jobatm.WorkerService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntacs.jobatm.WorkerService.util.AttendanceStatus;
import com.syntacs.jobatm.WorkerService.util.CompletionStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "worker_assigned_jobs_history")
public class WorkerAssignedJobsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignedHistoryId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotNull
    @Column(nullable = false)
    private Long jobId;

    
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;   // PRESENT -ONTIME, PRESENT -LATE, ABSENT -NEVER ARRIVED

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompletionStatus completionStatus;    // ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED

    private Double employerRating;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    private Double wagePaid;

    

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Size(max = 50)
    private String createdBy;

    @Size(max = 50)
    private String updatedBy;


    @PrePersist
    @PreUpdate
    public void validateAssignedDates() {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or same as endDate");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkerAssignedJobsHistory)) return false;

        WorkerAssignedJobsHistory that = (WorkerAssignedJobsHistory) o;
        if (this.assignedHistoryId == null || that.assignedHistoryId == null) return false;

        return this.assignedHistoryId.equals(that.assignedHistoryId);
    }

    @Override
    public int hashCode() {
        return assignedHistoryId != null ? assignedHistoryId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerAssignedJobsHistory {" +
                "assignedHistoryId=" + assignedHistoryId +
                ", workerId=" + (worker != null ? worker.getWorkerId() : null) +
                ", jobId=" + jobId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", completionStatus=" + completionStatus +
                ", employerRating=" + employerRating +
                ", feedback='" + feedback + '\'' +
                ", wagePaid=" + wagePaid +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}