package com.syntacs.jobatm.WorkerService.repository;

import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.entity.WorkerAppliedJobsHistory;
import com.syntacs.jobatm.WorkerService.util.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerAppliedJobsHistoryRepository extends JpaRepository<WorkerAppliedJobsHistory, Long> {

    List<WorkerAppliedJobsHistory> findByWorker(Worker worker);

    List<WorkerAppliedJobsHistory> findByWorkerAndApplicationStatus(Worker worker, ApplicationStatus status);
}