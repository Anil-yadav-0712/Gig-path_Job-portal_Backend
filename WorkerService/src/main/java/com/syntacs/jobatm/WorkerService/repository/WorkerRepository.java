package com.syntacs.jobatm.WorkerService.repository;

import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.entity.Worker;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    Optional<Worker> findByWorkerEmail(String email);

    boolean existsByWorkerEmail(String workerEmail);

    Optional<Worker> findByWorkerPhone(String phone);

    boolean existsByWorkerPhone(String workerPhone);

    // Optional<Worker> findByAadhaarNumber(String aadhaarNumber);

    // boolean existsByAadhaarNumber(String aadhaarNumber);

    Optional<Worker> findByNfcCardId(String nfcCardId);

    boolean existsByNfcCardId(String nfcCardId);

    // @Query("SELECT w FROM Worker w WHERE w.gigLevel >= :minGigLevel AND
    // w.gigLevel <= :maxGigLevel")
    // Set<Worker> findByGigLevelRange(double minGigLevel, double maxGigLevel);
}