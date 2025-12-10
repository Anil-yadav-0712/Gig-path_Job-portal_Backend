package com.syntacs.jobatm.WorkerService.repository;

import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.entity.WorkerDocument;
import com.syntacs.jobatm.WorkerService.util.VerificationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.List;

@Repository
public interface WorkerDocumentRepository extends JpaRepository<WorkerDocument, Long> {

    Set<WorkerDocument> findByWorker(Worker worker);

    WorkerDocument findByDocumentId(Long documentId);

    WorkerDocument findByDocumentIssueNumber(String documentIssueNumber);

    WorkerDocument findByDocumentName(String documentName);

    WorkerDocument findByDocumentVerificationStatus(VerificationStatus documentVerificationStatus);
}