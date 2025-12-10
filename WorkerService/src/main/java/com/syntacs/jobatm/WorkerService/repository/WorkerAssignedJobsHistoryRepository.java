// package com.syntacs.jobatm.WorkerService.repository;

// import com.syntacs.jobatm.WorkerService.entity.Worker;
// import com.syntacs.jobatm.WorkerService.entity.WorkerAssignedJobsHistory;
// import com.syntacs.jobatm.WorkerService.util.CompletionStatus;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;

// @Repository
// public interface WorkerAssignedJobsHistoryRepository extends JpaRepository<WorkerAssignedJobsHistory, Long> {

//     List<WorkerAssignedJobsHistory> findByWorker(Worker worker);

//     List<WorkerAssignedJobsHistory> findByWorkerAndCompletionStatus(Worker worker, CompletionStatus status);
// }
