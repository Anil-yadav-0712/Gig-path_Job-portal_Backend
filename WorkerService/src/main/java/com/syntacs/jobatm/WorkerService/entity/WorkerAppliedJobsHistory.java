// package com.syntacs.jobatm.WorkerService.entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.syntacs.jobatm.WorkerService.util.ApplicationStatus;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
// import lombok.Data;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;
// import java.time.LocalDateTime;

// @Data
// @Entity
// @Table(name = "worker_applied_jobs_history")
// public class WorkerAppliedJobsHistory {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long jobApplicationHistoryId;

//     @Column(nullable = false)
//     private long jobId;

//     @JsonIgnore
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "worker_id", nullable = false)
//     private Worker worker;

//     // @JsonIgnore
//     // @ManyToOne(fetch = FetchType.LAZY)
//     // @JoinColumn(name = "employer_id", nullable = false)
//     // private Employer postedBy;

//     @NotNull
//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private ApplicationStatus applicationStatus; // PENDING, SHORTLISTED, APPROVED, REJECTED, CANCELLED

//     @Column(nullable = true, columnDefinition = "TEXT")
//     private String rejectionReason;

//     @CreationTimestamp
//     @Column(nullable = false, updatable = false)
//     private LocalDateTime appliedAtTime;

//     @UpdateTimestamp
//     @Column(nullable = false)
//     private LocalDateTime updatedAtTime;

//     // Constructors
//     public WorkerAppliedJobsHistory() {
//     }

//     public WorkerAppliedJobsHistory(Worker worker, long jobId, ApplicationStatus applicationStatus) {
//         this.worker = worker;
//         this.jobId = jobId;
//         this.applicationStatus = applicationStatus;
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (!(o instanceof WorkerAppliedJobsHistory))
//             return false;

//         WorkerAppliedJobsHistory that = (WorkerAppliedJobsHistory) o;
//         if (this.jobApplicationHistoryId == null || that.jobApplicationHistoryId == null)
//             return false;

//         return this.jobApplicationHistoryId.equals(that.jobApplicationHistoryId);
//     }

//     @Override
//     public int hashCode() {
//         return jobApplicationHistoryId != null ? jobApplicationHistoryId.hashCode() : 0;
//     }

//     @Override
//     public String toString() {
//         return "WorkerAppliedJobsHistory {" +
//                 "Applied history id = " + jobApplicationHistoryId +
//                 ", Worker id = " + (worker != null ? worker.getWorkerId() : null) +
//                 ", Job id = " + jobId +
//                 // ", Posted by = "+ postedBy +
//                 ", Application status = " + applicationStatus +
//                 ", Rejection reason = '" + rejectionReason + '\'' +
//                 ", Applied at time = " + appliedAtTime +
//                 ", Updated at time = " + updatedAtTime +
//                 '}';
//     }
// }