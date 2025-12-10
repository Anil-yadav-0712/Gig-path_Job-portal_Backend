// package com.syntacs.jobatm.WorkerService.entity;

// import com.fasterxml.jackson.annotation.JsonIgnore;
// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
// import lombok.Data;
// import org.hibernate.annotations.UpdateTimestamp;
// import java.time.LocalDateTime;

// @Data
// @Entity
// @Table(name = "worker_location_history")
// public class WorkerLocationHistory {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long locationHistoryId;

//     @JsonIgnore  // Prevent infinite recursion during serialization
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "worker_id", nullable = false)
//     private Worker worker;

//     @NotBlank
//     @Size(max = 255)
//     @Column(nullable = false)
//     private String location;

//     @UpdateTimestamp
//     @Column(nullable = false)
//     private LocalDateTime lastUpdatedAt;


//     // Constructors
//     public WorkerLocationHistory() {}

//     public WorkerLocationHistory(Worker worker, String location) {
//         this.worker = worker;
//         this.location = location;
//     }


//     @Override
//     public boolean equals(Object o) {
//         if (this == o) return true;
//         if (!(o instanceof WorkerLocationHistory)) return false;
//         WorkerLocationHistory that = (WorkerLocationHistory) o;

//         if (this.locationHistoryId == null || that.locationHistoryId == null)
//             return false;

//         return this.locationHistoryId.equals(that.locationHistoryId);
//     }

//     @Override
//     public int hashCode() {
//         return locationHistoryId != null ? locationHistoryId.hashCode() : 0;
//     }

//     @Override
//     public String toString() {
//         return "WorkerLocationHistory {" +
//                 "locationHistoryId=" + locationHistoryId +
//                 ", workerId=" + (worker != null ? worker.getWorkerId() : null) +
//                 ", location='" + location + '\'' +
//                 ", lastUpdatedAt=" + lastUpdatedAt +
//                 '}';
//     }
// }