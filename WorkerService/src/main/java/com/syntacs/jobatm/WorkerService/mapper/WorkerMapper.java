package com.syntacs.jobatm.WorkerService.mapper;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.entity.WorkerDocument;
import com.syntacs.jobatm.WorkerService.entity.WorkerSkill;
import com.syntacs.jobatm.WorkerService.util.AgeCalculator;
import com.syntacs.jobatm.WorkerService.util.DocumentObject;
import com.syntacs.jobatm.WorkerService.util.Skill;
import com.syntacs.jobatm.WorkerService.util.WorkerCardObject;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WorkerMapper {

    // --------------------------------------------------------------
    // DTO → ENTITY
    public Worker toEntity(WorkerRegistrationDTO dto) {

        Worker worker = new Worker();

        // BASIC DETAILS
        worker.setWorkerName(dto.getWorkerName());
        worker.setBirthDate(dto.getBirthDate());
        worker.setWorkerPhone(dto.getWorkerPhone());
        worker.setWorkerEmail(dto.getWorkerEmail());
        worker.setWorkerPasswordHash(dto.getWorkerPassword());

        // PROFILE PHOTO
        if (dto.getProfilePhoto() != null && !dto.getProfilePhoto().isEmpty()) {
            try {
                worker.setProfilePhoto(dto.getProfilePhoto().getBytes());
            } catch (IOException e) {
                throw new IllegalStateException("Failed to read profile photo", e);
            }
        }

        // ADDRESS + LOCATION
        worker.setWorkerAddress(dto.getWorkerAddress());
        worker.setLatitude(dto.getLatitude());
        worker.setLongitude(dto.getLongitude());
        worker.setPreferredTravelDistance(dto.getPreferredTravelDistance());
        worker.setPreferredLanguage(dto.getPreferredLanguage());

        // EDUCATION DETAILS
        if (dto.getWorkerEducationDetails() != null) {
            worker.setWorkerEducationDetails(dto.getWorkerEducationDetails());
        }

        // DOCUMENTS
        if (dto.getWorkerDocuments() != null) {
            Set<WorkerDocument> docEntities = dto.getWorkerDocuments().stream()
                    .map(d -> {
                        WorkerDocument wd = new WorkerDocument();
                        wd.setWorker(worker);

                        wd.setDocumentName(d.getDocumentName());
                        wd.setDocumentIssueNumber(d.getDocumentIssueNumber());
                        wd.setIssuingAuthority(d.getIssuingAuthority());
                        wd.setIssueDate(d.getIssueDate());
                        wd.setDocumentValidity(d.getDocumentValidity());
                        wd.setDocumentVerificationStatus(d.getDocumentVerificationStatus());
                        wd.setVerifiedAt(d.getVerifiedAt());
                        wd.setVerifiedBy(d.getVerifiedBy()); // nullable
                        wd.setRejectionReason(d.getRejectionReason());
                        wd.setDeletionReason(d.getDeletionReason());

                        if (d.getFile() != null && !d.getFile().isEmpty()) {
                            try {
                                wd.setFileData(d.getFile().getBytes());
                            } catch (IOException e) {
                                throw new IllegalStateException("Failed to read document file", e);
                            }
                        }

                        return wd;
                    }).collect(Collectors.toSet());

            worker.setDocuments(docEntities);
        }

        // SKILLS
        if (dto.getSkills() != null) {
            Set<WorkerSkill> skillEntities = dto.getSkills().stream()
                    .map(s -> new WorkerSkill(worker, s.name()))
                    .collect(Collectors.toSet());
            worker.setSkills(skillEntities);
        }

        // META
        worker.setCreatedBy(dto.getCreatedBy());
        worker.setKioskIdOfRegistration(dto.getKioskIdOfRegistration());
        worker.setUpdatedBy(dto.getUpdatedBy());
        worker.setKioskIdOfUpdation(dto.getKioskIdOfUpdation());

        // CAREER INFO
        worker.setWorkCategory(dto.getWorkCategory());
        worker.setMinWageRange(dto.getMinWageRange());
        worker.setMaxWageRange(dto.getMaxWageRange());

        return worker;
    }

    // -------------------------------------------------------------------------
    // ENTITY → RESPONSE DTO
    public WorkerResponseDTO toResponseDTO(Worker worker) {
        WorkerResponseDTO dto = new WorkerResponseDTO();

        dto.setWorkerId(worker.getWorkerId());
        dto.setWorkerName(worker.getWorkerName());
        dto.setBirthDate(worker.getBirthDate());
        dto.setAge(AgeCalculator.calculateAge(worker.getBirthDate()));

        if (worker.getProfilePhoto() != null) {
            dto.setProfilePhoto(Base64.getEncoder().encodeToString(worker.getProfilePhoto()));
        }

        dto.setWorkerPhone(worker.getWorkerPhone());
        dto.setWorkerEmail(worker.getWorkerEmail());
        dto.setWorkerAddress(worker.getWorkerAddress());

        // null-safe location
        dto.setLocation(worker.getLatitude() != null && worker.getLongitude() != null
                ? worker.getLatitude() + ", " + worker.getLongitude()
                : null);

        dto.setPreferredTravelDistance(worker.getPreferredTravelDistance());
        dto.setPreferredLanguage(worker.getPreferredLanguage());

        dto.setEducationDetails(worker.getWorkerEducationDetails());

        // DOCUMENTS - null-safe verifiedBy
        if (worker.getDocuments() != null) {
            Set<DocumentObject> docs = worker.getDocuments().stream().map(wd -> {
                DocumentObject d = new DocumentObject();
                d.setDocumentName(wd.getDocumentName());
                d.setDocumentIssueNumber(wd.getDocumentIssueNumber());
                d.setIssuingAuthority(wd.getIssuingAuthority());
                d.setIssueDate(wd.getIssueDate());
                d.setDocumentValidity(wd.getDocumentValidity());

                if (wd.getFileData() != null) {
                    d.setFile(Base64.getEncoder().encodeToString(wd.getFileData()));
                }

                d.setDocumentVerificationStatus(wd.getDocumentVerificationStatus());
                d.setVerifiedAt(wd.getVerifiedAt());

                // ⚠ null-safe
                d.setVerifiedBy(wd.getVerifiedBy() != null ? wd.getVerifiedBy() : null);

                d.setRejectionReason(wd.getRejectionReason());
                d.setCreatedAtTime(wd.getCreatedAtTime());
                d.setUpdatedAtTime(wd.getUpdatedAtTime());
                d.setDeletedAtTime(wd.getDeletedAtTime());
                d.setDeletionReason(wd.getDeletionReason());
                return d;
            }).collect(Collectors.toSet());

            dto.setDocuments(docs);
        }

        // SKILLS
        if (worker.getSkills() != null) {
            dto.setSkills(
                    worker.getSkills().stream()
                            .map(ws -> Skill.valueOf(ws.getSkill()))
                            .collect(Collectors.toSet()));
        }

        dto.setIsVerified(worker.getIsVerified());
        dto.setNfcCardStatus(worker.getNfcCardStatus());
        dto.setNfcCardId(worker.getNfcCardId());
        dto.setWorkCategory(worker.getWorkCategory());
        dto.setMinWageRange(worker.getMinWageRange());
        dto.setMaxWageRange(worker.getMaxWageRange());
        dto.setWorkAcceptanceRate(worker.getWorkAcceptanceRate());
        dto.setWorkCompletionRate(worker.getWorkCompletionRate());
        dto.setTrustScore(worker.getWorkerTrustScore());
        dto.setWorkerLevel(worker.getWorkerLevel());

        dto.setCreatedBy(worker.getCreatedBy());
        dto.setKioskIdOfRegistration(worker.getKioskIdOfRegistration());
        dto.setCreatedAtTime(worker.getCreatedAtTime());
        dto.setUpdatedAtTime(worker.getUpdatedAtTime());

        return dto;
    }

    // -------------------------------------------------------------------------
    // ENTITY → Worker Card Object
    public WorkerCardObject toCardDTO(Worker worker) {
        WorkerCardObject card = new WorkerCardObject();

        if (worker.getProfilePhoto() != null) {
            card.setProfilePhoto(Base64.getEncoder().encodeToString(worker.getProfilePhoto()));
        }

        card.setWorkerName(worker.getWorkerName());
        card.setAge(AgeCalculator.calculateAge(worker.getBirthDate()));

        if (worker.getWorkerAddress() != null) {
            card.setCity(worker.getWorkerAddress().getCity() + ", "
                    + worker.getWorkerAddress().getState() + ", "
                    + worker.getWorkerAddress().getCountry());
        }

        card.setLanguage(worker.getPreferredLanguage());
        card.setVerified(worker.getIsVerified());
        card.setWorkCategory(worker.getWorkCategory());

        if (worker.getSkills() != null) {
            card.setSkills(
                    worker.getSkills().stream()
                            .map(ws -> Skill.valueOf(ws.getSkill()))
                            .collect(Collectors.toSet()));
        }

        card.setTrustScore(worker.getWorkerTrustScore());
        card.setWorkerLevel(worker.getWorkerLevel());

        return card;
    }
}