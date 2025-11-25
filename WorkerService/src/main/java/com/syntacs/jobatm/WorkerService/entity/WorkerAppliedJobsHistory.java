package com.syntacs.jobatm.WorkerService.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntacs.jobatm.WorkerService.util.ApplicationStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;

@Data
@Entity
@Table(name = "worker_applied_jobs_history")
public class WorkerAppliedJobsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appliedHistoryId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotNull
    @Column(nullable = false)
    private Long jobId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus applicationStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    // @Size(max = 50)
    // private String createdBy;

    // @Size(max = 50)
    // private String updatedBy;


    // Constructors
    public WorkerAppliedJobsHistory() {}

    public WorkerAppliedJobsHistory(Worker worker, Long jobId, ApplicationStatus applicationStatus) {
        this.worker = worker;
        this.jobId = jobId;
        this.applicationStatus = applicationStatus;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkerAppliedJobsHistory)) return false;

        WorkerAppliedJobsHistory that = (WorkerAppliedJobsHistory) o;
        if (this.appliedHistoryId == null || that.appliedHistoryId == null) return false;

        return this.appliedHistoryId.equals(that.appliedHistoryId);
    }

    @Override
    public int hashCode() {
        return appliedHistoryId != null ? appliedHistoryId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerAppliedJobsHistory {" +
                "appliedHistoryId=" + appliedHistoryId +
                ", workerId=" + (worker != null ? worker.getWorkerId() : null) +
                ", jobId=" + jobId +
                ", applicationStatus=" + applicationStatus +
                ", appliedAt=" + appliedAt +
                ", updatedAt=" + updatedAt +
                ", rejectionReason='" + rejectionReason + '\'' +
                '}';
    }
}