package com.syntacs.jobatm.WorkerService.mapper;

import org.springframework.stereotype.Component;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;

@Component
public class WorkerMapper {

    // Convert DTO → Entity
    public static Worker toEntity(WorkerRegistrationDTO dto) {
        if (dto == null)
            return null;

        Worker worker = new Worker();
        // worker.setWorkerId(dto.getWorkerId());
        worker.setWorkerName(dto.getWorkerName());
        worker.setBirthDate(dto.getBirthDate());
        worker.setWorkerPhone(dto.getWorkerPhone());
        worker.setWorkerEmail(dto.getWorkerEmail());
        worker.setWorkerPasswordHash(dto.getWorkerPassword()); // need to hash before saving
        worker.setAadhaarNumber(dto.getAadhaarNumber());
        worker.setPanNumber(dto.getPanNumber());
        worker.setVoterCardNumber(dto.getVoterCardNumber());
        worker.setLocation(dto.getLocation());
        worker.setWorkCategory(dto.getWorkCategory());
        worker.setPreferredTravelDistance(dto.getPreferredTravelDistance());
        worker.setMinWageRange(dto.getMinWageRange());
        worker.setMaxWageRange(dto.getMaxWageRange());
        worker.setCreatedBy(dto.getCreatedBy());
        worker.setUpdatedBy(dto.getUpdatedBy());
        worker.setPreferredLanguage(dto.getPreferredLanguage());
        worker.setGigLevel(dto.getGigLevel());
        // worker.setUpdatedAt(dto.getUpdatedAt());
        worker.setKioskIdOfRegistration(dto.getKioskIdOfRegistration());

        // Documents
        // if (dto.getDocuments() != null) {
        // worker.setDocuments(
        // dto.getDocuments().stream()
        // .map(docDTO -> {
        // WorkerDocument doc = new WorkerDocument();
        // doc.setWorker(worker);
        // doc.setDocumentType(docDTO.getDocumentType());
        // doc.setDocumentNumber(docDTO.getDocumentNumber());
        // return doc;
        // }).collect(Collectors.toSet())
        // );
        // }

        // Defaults
        if (worker.getNfcCardStatus() == null)
            worker.setNfcCardStatus(NfcCardStatus.PENDING);
        if (worker.getIsVerified() == null)
            worker.setIsVerified(false);
        if (worker.getGigLevel() == null)
            worker.setGigLevel(1.0);

        return worker;
    }

    // Convert Entity → DTO
    public static WorkerResponseDTO toDTO(Worker worker) {
        if (worker == null)
            return null;

        WorkerResponseDTO dto = new WorkerResponseDTO();
        dto.setWorkerId(worker.getWorkerId());
        dto.setWorkerName(worker.getWorkerName());
        // dto.setBirthDate(worker.getBirthDate());
        dto.setAge(worker.getAge());
        dto.setWorkerPhone(worker.getWorkerPhone());
        dto.setWorkerEmail(worker.getWorkerEmail());
        dto.setLocation(worker.getLocation());
        dto.setWorkCategory(worker.getWorkCategory());
        dto.setPreferredTravelDistance(worker.getPreferredTravelDistance());
        dto.setMinWageRange(worker.getMinWageRange());
        dto.setMaxWageRange(worker.getMaxWageRange());
        dto.setCreatedBy(worker.getCreatedBy());
        dto.setKioskIdOfRegistration(worker.getKioskIdOfRegistration());
        dto.setNfcCardStatus(worker.getNfcCardStatus());
        dto.setIsVerified(worker.getIsVerified());
        dto.setGigLevel(worker.getGigLevel());

        // Documents
        // if (worker.getDocuments() != null) {
        // dto.setDocuments(
        // worker.getDocuments().stream()
        // .map(doc -> {
        // WorkerDocumentDTO docDTO = new WorkerDocumentDTO();
        // docDTO.setDocumentId(doc.getDocumentId());
        // docDTO.setDocumentType(doc.getDocumentType());
        // docDTO.setDocumentNumber(doc.getDocumentNumber());
        // docDTO.setVerificationStatus(doc.getVerificationStatus());
        // docDTO.setVerifiedBy(doc.getVerifiedBy());
        // docDTO.setVerifiedAt(doc.getVerifiedAt());
        // docDTO.setRejectionReason(doc.getRejectionReason());
        // return docDTO;
        // }).collect(Collectors.toSet())
        // );
        // }
        // Skills, appliedJobs, assignedJobs, locationHistory can also be mapped
        // similarly if needed

        return dto;
    }
}