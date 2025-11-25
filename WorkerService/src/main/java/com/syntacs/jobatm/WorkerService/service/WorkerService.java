package com.syntacs.jobatm.WorkerService.service;

import java.util.List;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;

public interface WorkerService {

    WorkerResponseDTO registerWorker(WorkerRegistrationDTO workerInputData);

    WorkerResponseDTO getWorker(Long workerId);

    List<WorkerResponseDTO> getAllWorkers();

    WorkerResponseDTO updateWorker(Long workerId, WorkerRegistrationDTO workerDTO);

    void deleteWorker(Long workerId);

    WorkerResponseDTO verifyWorkerByAdmin(Long workerId);

    WorkerResponseDTO issueNfcCard(Long workerId);

    WorkerResponseDTO recalculateGigLevel(Long workerId);
}
