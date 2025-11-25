package com.syntacs.jobatm.WorkerService.repository;

import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.util.RegistrationSource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

    List<Worker> findByWorkerPhone(String phone);
    Optional<Worker> findByWorkerEmail(String email);
    boolean existsByWorkerPhone(String workerPhone);    

    boolean existsByAadhaarNumber(String aadhaarNumber);
    Optional<Worker> findByAadhaarNumber(String aadhaarNumber);

    Optional<Worker> findByNfcCardId(String nfcCardId);

    List<Worker> findByLocation(String location);
    
    
    @Query("SELECT w FROM Worker w WHERE w.gigLevel >= :minGigLevel AND w.gigLevel <= :maxGigLevel")
    List<Worker> findByGigLevelRange(Double minGigLevel, Double maxGigLevel);
    
    Optional<String> findByKioskIdOfRegistration(String kioskIdOfRegistration);
}
