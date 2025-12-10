// package com.syntacs.jobatm.WorkerService.entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.syntacs.jobatm.WorkerService.util.AttendanceStatus;
// import com.syntacs.jobatm.WorkerService.util.CompletionStatus;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;
// import lombok.Data;

// import org.hibernate.annotations.CreationTimestamp;
// import org.hibernate.annotations.UpdateTimestamp;
// import java.time.LocalDateTime;

// @Entity
// @Data
// @Table(name = "worker_assigned_jobs_history")
// public class WorkerAssignedJobsHistory {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long jobAssignmentHistoryId;

//     @Column(nullable = false)
//     private long jobId;

//     // @JsonIgnore
//     // @ManyToOne(fetch = FetchType.LAZY)
//     // @JoinColumn(name = "employer_id", nullable = false)
//     // private Employer assignedBy;

//     @JsonIgnore
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "worker_id", nullable = false)
//     private Worker assignedTo;

//     @Enumerated(EnumType.STRING)
//     private AttendanceStatus attendanceStatus; // PRESENT -ONTIME, PRESENT -LATE, ABSENT -NEVER ARRIVED

//     @NotNull
//     @Enumerated(EnumType.STRING)
//     @Column(nullable = false)
//     private CompletionStatus completionStatus = CompletionStatus.ASSIGNED; // ASSIGNED, IN_PROGRESS, COMPLETED,
//                                                                            // CANCELLED

//     @Column(nullable = false)
//     @Size(max = 10)
//     private double wagePaid;

//     // private Float employerRating;
//     // private Float workerRating;

//     // @Column(columnDefinition = "TEXT")
//     // private String feedback;

//     @CreationTimestamp
//     @Column(nullable = false, updatable = false)
//     private LocalDateTime createdAtTime;

//     @UpdateTimestamp
//     @Column(nullable = false)
//     private LocalDateTime updatedAtTime;

//     @UpdateTimestamp
//     private LocalDateTime startedAtTime;

//     @UpdateTimestamp
//     private LocalDateTime completedAtTime;

//     @PrePersist
//     @PreUpdate
//     public void validateAssignedDates() {
//         if (startedAtTime != null && completedAtTime != null && startedAtTime.isAfter(completedAtTime)) {
//             throw new IllegalArgumentException("startDate must be before or same as endDate");
//         }
//     }

//     @Override
//     public boolean equals(Object o) {
//         if (this == o)
//             return true;
//         if (!(o instanceof WorkerAssignedJobsHistory))
//             return false;

//         WorkerAssignedJobsHistory that = (WorkerAssignedJobsHistory) o;
//         if (this.jobAssignmentHistoryId == null || that.jobAssignmentHistoryId == null)
//             return false;

//         return this.jobAssignmentHistoryId.equals(that.jobAssignmentHistoryId);
//     }

//     @Override
//     public int hashCode() {
//         return jobAssignmentHistoryId != null ? jobAssignmentHistoryId.hashCode() : 0;
//     }

//     @Override
//     public String toString() {
//         return "WorkerAssignedJobsHistory {" +
//                 "Assigned history id = " + jobAssignmentHistoryId +
//                 // ", Assigned by Employer = " + assignedBy +
//                 ", Assigned to worker = " + assignedTo +
//                 // ", Assigned to Worker id = " + (assignedTo != null ? assignedTo.getWorkerId()
//                 // : null) +
//                 ", Job id = " + jobId +
//                 ", Worker attendance status = " + attendanceStatus +
//                 ", Task completion status = " + completionStatus +
//                 ", Wage paid = " + wagePaid +
//                 ", Task Assignment created time = " + createdAtTime +
//                 ", Task Assignment updated time = " + updatedAtTime +
//                 ", When work started = " + startedAtTime +
//                 ", When work completed = " + completedAtTime +
//                 // ", Employer rating = " + employerRating +
//                 // ", Worker rating = " + workerRating +
//                 // ", feedback='" + feedback + '\'' +
//                 '}';
//     }
// }