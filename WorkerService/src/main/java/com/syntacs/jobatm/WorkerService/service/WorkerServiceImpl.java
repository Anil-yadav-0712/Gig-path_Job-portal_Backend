package com.syntacs.jobatm.WorkerService.service;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.mapper.WorkerMapper;
import com.syntacs.jobatm.WorkerService.repository.WorkerRepository;
import com.syntacs.jobatm.WorkerService.util.CompletionStatus;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final RestTemplate restTemplate;

    public WorkerServiceImpl(WorkerRepository workerRepository,
                             RestTemplate restTemplate) {
        this.workerRepository = workerRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public WorkerResponseDTO registerWorker(WorkerRegistrationDTO workerDTO) {
        Worker worker = WorkerMapper.toEntity(workerDTO);

        // Initialize default values
        worker.setNfcCardStatus(NfcCardStatus.PENDING);
        worker.setIsVerified(false);
        worker.setGigLevel(1.0);

        Worker saved = workerRepository.save(worker);
        return WorkerMapper.toDTO(saved);
    }

    @Override
    public WorkerResponseDTO getWorker(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return WorkerMapper.toDTO(worker);
    }

    @Override
    public List<WorkerResponseDTO> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(WorkerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkerResponseDTO updateWorker(Long workerId, WorkerRegistrationDTO workerDTO) {
        Worker existing = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        // Update allowed fields (manual mapping or use an update method in mapper)
        existing.setWorkerName(workerDTO.getWorkerName());
        existing.setBirthDate(workerDTO.getBirthDate());
        existing.setWorkerPhone(workerDTO.getWorkerPhone());
        existing.setWorkerEmail(workerDTO.getWorkerEmail());
        existing.setLocation(workerDTO.getLocation());
        existing.setWorkCategory(workerDTO.getWorkCategory());
        existing.setPreferredTravelDistance(workerDTO.getPreferredTravelDistance());
        existing.setMinWageRange(workerDTO.getMinWageRange());
        existing.setMaxWageRange(workerDTO.getMaxWageRange());
        existing.setKioskIdOfRegistration(workerDTO.getKioskIdOfRegistration());

        Worker updated = workerRepository.save(existing);
        return WorkerMapper.toDTO(updated);
    }

    @Override
    public void deleteWorker(Long workerId) {
        workerRepository.deleteById(workerId);
    }

    @Override
    public WorkerResponseDTO verifyWorkerByAdmin(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        worker.setIsVerified(true);
        Worker updated = workerRepository.save(worker);

        // Here you can use restTemplate to notify other microservices if needed
        return WorkerMapper.toDTO(updated);
    }

    @Override
    public WorkerResponseDTO issueNfcCard(Long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!worker.getIsVerified()) {
            throw new RuntimeException("Worker not verified by admin yet");
        }

        // Generate a simple NFC card ID
        String nfcId = "NFC-" + worker.getWorkerId() + "-" + System.currentTimeMillis();
        worker.setNfcCardId(nfcId);
        worker.setNfcCardStatus(NfcCardStatus.ACTIVE);

        Worker updated = workerRepository.save(worker);
        return WorkerMapper.toDTO(updated);
    }

   @Override
public WorkerResponseDTO recalculateGigLevel(Long workerId) {
    Worker worker = workerRepository.findById(workerId)
            .orElseThrow(() -> new RuntimeException("Worker not found"));

    double totalJobs = worker.getAssignedJobs().size();
    double completedJobs = worker.getAssignedJobs().stream()
            .filter(j -> j.getCompletionStatus() == CompletionStatus.COMPLETED)
            .count();

    double gigLevel = totalJobs == 0 ? 1.0 : (completedJobs / totalJobs) * 5; // scale 1–5
    worker.setGigLevel(Math.min(Math.max(gigLevel, 1.0), 5.0));

    Worker updated = workerRepository.save(worker);
    return WorkerMapper.toDTO(updated);
}
}
