package com.syntacs.jobatm.WorkerService.repository;

import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.entity.WorkerDocument;
import com.syntacs.jobatm.WorkerService.util.DocumentVerificationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerDocumentRepository extends JpaRepository<WorkerDocument, Long> {

    List<WorkerDocument> findByWorker(Worker worker);

    List<WorkerDocument> findByVerificationStatus(DocumentVerificationStatus status);
}