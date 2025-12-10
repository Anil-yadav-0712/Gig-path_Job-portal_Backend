package com.syntacs.jobatm.WorkerService.service;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.entity.Worker;
import com.syntacs.jobatm.WorkerService.entity.WorkerDocument;
import com.syntacs.jobatm.WorkerService.entity.WorkerSkill;
import com.syntacs.jobatm.WorkerService.mapper.WorkerMapper;
import com.syntacs.jobatm.WorkerService.repository.WorkerDocumentRepository;
import com.syntacs.jobatm.WorkerService.repository.WorkerRepository;
import com.syntacs.jobatm.WorkerService.repository.WorkerSkillRepository;
import com.syntacs.jobatm.WorkerService.util.CompletionStatus;
import com.syntacs.jobatm.WorkerService.util.DocumentObject;
import com.syntacs.jobatm.WorkerService.util.EducationDetailsObject;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;
import com.syntacs.jobatm.WorkerService.util.Skill;
import com.syntacs.jobatm.WorkerService.util.VerificationStatus;
import com.syntacs.jobatm.WorkerService.util.WorkerCardObject;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    private final WorkerRepository workerRepository;
    private final WorkerSkillRepository workerSkillRepository;
    private final WorkerDocumentRepository workerDocumentRepository;
    private final WorkerMapper workerMapper;

    public WorkerServiceImpl(
            WorkerRepository workerRepository,
            WorkerSkillRepository workerSkillRepository,
            WorkerDocumentRepository workerDocumentRepository,
            WorkerMapper workerMapper) {
        this.workerRepository = workerRepository;
        this.workerSkillRepository = workerSkillRepository;
        this.workerDocumentRepository = workerDocumentRepository;
        this.workerMapper = workerMapper;
    }

    // 1️⃣ Worker Registration
    @Override
    public WorkerResponseDTO registerWorker(WorkerRegistrationDTO workerRegDTO) {
        Worker worker = workerMapper.toEntity(workerRegDTO);

        // Initialize default values
        worker.setNfcCardStatus(NfcCardStatus.PENDING);
        worker.setIsVerified(false);
        worker.setWorkerLevel(1);
        worker.setWorkerTrustScore(0);

        if (workerRegDTO.getSkills() != null) {
            Set<WorkerSkill> skills = workerRegDTO.getSkills().stream()
                    .map(skillEnum -> new WorkerSkill(worker, skillEnum.name()))
                    .collect(Collectors.toSet());
            worker.setSkills(skills);
        }

        Set<WorkerDocument> documentEntities = new HashSet<>();
        for (DocumentObject doc : workerRegDTO.getWorkerDocuments()) {

            WorkerDocument entity = new WorkerDocument(
                    worker,
                    doc.getDocumentName(),
                    doc.getDocumentIssueNumber(),
                    doc.getIssuingAuthority(),
                    doc.getIssueDate(),
                    doc.getDocumentValidity());
            // Handle file upload → byte[]
            if (doc.getFile() != null && !doc.getFile().isEmpty()) {
                try {
                    entity.setFileData(doc.getFile().getBytes());
                } catch (IOException ignored) {
                }
            }
            entity.setDocumentVerificationStatus(VerificationStatus.PENDING);
            documentEntities.add(entity);
        }
        worker.setDocuments(documentEntities);

        Worker saved = workerRepository.save(worker);
        // Return mapped response
        return workerMapper.toResponseDTO(saved);
    }

    // 2️⃣ Worker Login
    @Override
    public WorkerResponseDTO login(String username, String passwordHash) {

        Optional<Worker> workerOptional = workerRepository.findByWorkerEmail(username)
                .or(() -> workerRepository.findByWorkerPhone(username));

        Worker worker = workerOptional.orElseThrow(() -> new RuntimeException("Username does not exist"));

        // TEMPORARY: plain-text comparison
        if (!passwordHash.equals(worker.getWorkerPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }
        return workerMapper.toResponseDTO(worker);
    }

    // 3️⃣ Get all Workers List
    @Override
    public List<WorkerResponseDTO> getAllWorkers() {
        return workerRepository.findAll().stream()
                .map(workerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // 4️⃣ Get a single Worker details by id
    @Override
    public WorkerResponseDTO getWorker(long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return workerMapper.toResponseDTO(worker);
    }

    // 5️⃣ Search by phone/mobile number
    @Override
    public WorkerResponseDTO searchByPhone(String phoneNo) {
        Worker worker = workerRepository.findByWorkerPhone(phoneNo)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return workerMapper.toResponseDTO(worker);
    }

    // 6️⃣ Search by email id
    @Override
    public WorkerResponseDTO searchByEmail(String email) {
        Worker worker = workerRepository.findByWorkerPhone(email)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return workerMapper.toResponseDTO(worker);
    }

    // 7️⃣ Update Worker details (modified)
    @Override
    public WorkerResponseDTO updateWorker(long workerId, WorkerRegistrationDTO workerUpdateDTO) {
        Worker existingWorker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        // Update basic fields
        existingWorker.setWorkerName(workerUpdateDTO.getWorkerName());
        existingWorker.setBirthDate(workerUpdateDTO.getBirthDate());
        existingWorker.setWorkerPhone(workerUpdateDTO.getWorkerPhone());
        existingWorker.setWorkerEmail(workerUpdateDTO.getWorkerEmail());
        existingWorker.setWorkCategory(workerUpdateDTO.getWorkCategory());
        existingWorker.setPreferredTravelDistance(workerUpdateDTO.getPreferredTravelDistance());
        existingWorker.setMinWageRange(workerUpdateDTO.getMinWageRange());
        existingWorker.setMaxWageRange(workerUpdateDTO.getMaxWageRange());
        existingWorker.setKioskIdOfRegistration(workerUpdateDTO.getKioskIdOfRegistration());
        existingWorker.setUpdatedBy(workerUpdateDTO.getUpdatedBy());
        existingWorker.setKioskIdOfUpdation(workerUpdateDTO.getKioskIdOfUpdation());

        // Update Skills (add new, do not remove existing)
        if (workerUpdateDTO.getSkills() != null) {
            Set<WorkerSkill> existingSkills = existingWorker.getSkills();
            for (Skill skillEnum : workerUpdateDTO.getSkills()) {
                boolean exists = existingSkills.stream()
                        .anyMatch(s -> s.getSkill().equals(skillEnum.name()));
                if (!exists) {
                    existingSkills.add(new WorkerSkill(existingWorker, skillEnum.name()));
                }
            }
        }

        // Update Documents (add new, do not remove existing)
        if (workerUpdateDTO.getWorkerDocuments() != null) {
            Set<WorkerDocument> existingDocs = existingWorker.getDocuments();
            for (DocumentObject docDto : workerUpdateDTO.getWorkerDocuments()) {
                boolean exists = existingDocs.stream()
                        .anyMatch(d -> d.getDocumentName().equals(docDto.getDocumentName())
                                && d.getDocumentIssueNumber().equals(docDto.getDocumentIssueNumber()));
                if (!exists) {
                    WorkerDocument newDoc = new WorkerDocument(
                            existingWorker,
                            docDto.getDocumentName(),
                            docDto.getDocumentIssueNumber(),
                            docDto.getIssuingAuthority(),
                            docDto.getIssueDate(),
                            docDto.getDocumentValidity());
                    if (docDto.getFile() != null && !docDto.getFile().isEmpty()) {
                        try {
                            newDoc.setFileData(docDto.getFile().getBytes());
                        } catch (IOException ignored) {
                        }
                    }
                    newDoc.setDocumentVerificationStatus(VerificationStatus.PENDING);
                    existingDocs.add(newDoc);
                }
            }
        }

        // Update EducationDetails (add new, do not remove existing)
        if (workerUpdateDTO.getWorkerEducationDetails() != null) {
            Set<EducationDetailsObject> existingEdu = existingWorker.getWorkerEducationDetails();
            for (EducationDetailsObject eduDto : workerUpdateDTO.getWorkerEducationDetails()) {
                boolean exists = existingEdu.stream()
                        .anyMatch(e -> e.getQualification().equals(eduDto.getQualification())
                                && e.getIdNumber().equals(eduDto.getIdNumber()));
                if (!exists) {
                    EducationDetailsObject newEdu = new EducationDetailsObject();
                    // newEdu.setWorker(existingWorker);
                    newEdu.setQualification(eduDto.getQualification());
                    newEdu.setInstituteName(eduDto.getInstituteName());
                    newEdu.setIssuingAuthority(eduDto.getIssuingAuthority());
                    newEdu.setIdNumber(eduDto.getIdNumber());
                    newEdu.setMarksObtained(eduDto.getMarksObtained());
                    newEdu.setTotalMarks(eduDto.getTotalMarks());
                    newEdu.setPercentage(eduDto.getPercentage());
                    // newEdu.setVerified(eduDto.isVerified());
                    existingEdu.add(newEdu);
                }
            }
        }

        Worker updatedWorker = workerRepository.save(existingWorker);
        return workerMapper.toResponseDTO(updatedWorker);
    }

    // 8️⃣ Delete Worker entry
    @Override
    public void deleteWorker(long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        worker.setDeletedAtTime(LocalDateTime.now());
        workerRepository.deleteById(workerId);
    }

    // 9️⃣ Search by Age Group Range
    @Override
    public Set<WorkerCardObject> searchWithinAgeRange(int minAge, int maxAge) {
        return new HashSet<>();
    }

    // 🔟 Search by Educational Qualification
    @Override
    public Set<WorkerCardObject> searchByQualification(Object qualification) {
        return new HashSet<>();
    }

    // 1️⃣1️⃣Search by Education institute
    @Override
    public Set<WorkerCardObject> searchByInstituteName(Object institute) {
        return new HashSet<>();
    }

    // 1️⃣2️⃣Search by Preferred Language
    @Override
    public Set<WorkerCardObject> searchByLanguage(Object language) {
        return new HashSet<>();
    }

    // 1️⃣3️⃣Search by city/town name
    @Override
    public Set<WorkerCardObject> searchByCity(String cityName) {
        return new HashSet<>();
    }

    // 1️⃣4️⃣Search by district name
    @Override
    public Set<WorkerCardObject> searchByDistrict(String districtName) {
        return new HashSet<>();
    }

    // 1️5️⃣Search by state name
    @Override
    public Set<WorkerCardObject> searchByState(String stateName) {
        return new HashSet<>();
    }

    // 1️⃣6️⃣Search by country name
    @Override
    public Set<WorkerCardObject> searchByCountry(String countryName) {
        return new HashSet<>();
    }

    // 1️⃣7️⃣Search by Kiosk id of Registration
    @Override
    public Set<WorkerCardObject> searchByKioskIdOfRegistration(Long kioskId) {
        return new HashSet<>();
    }

    // 1️⃣8️⃣Search by Trust score
    @Override
    public Set<WorkerCardObject> searchByTrustScore(int trustScore) {
        return new HashSet<>();
    }

    // 1️⃣9️⃣Search by worker Level
    @Override
    public Set<WorkerCardObject> searchByWorkerLevel(int workerLevel) {
        return new HashSet<>();
    }

    // 2️⃣0️⃣ Verification status set to true by admin - when all documents are
    @Override
    public WorkerResponseDTO verifyWorkerByAdmin(long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        worker.setIsVerified(true);
        Worker updated = workerRepository.save(worker);
        // Here you can use restTemplate to notify other microservices if needed
        return workerMapper.toResponseDTO(updated);
    }

    // 2️⃣1️⃣Issue of a nfc id - (currently an unique qr code)
    @Override
    public WorkerResponseDTO issueNfcCard(long workerId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (!worker.getIsVerified()) {
            throw new RuntimeException("Worker not verified by admin yet");
        }
        // Generate a simple NFC id sample sequence
        String nfcId = "NFC:" + worker.getWorkerId() + "-" + System.currentTimeMillis();
        worker.setNfcCardId(nfcId);
        worker.setNfcCardStatus(NfcCardStatus.ACTIVE);

        Worker updated = workerRepository.save(worker);
        return workerMapper.toResponseDTO(updated);
    }

    // 2️⃣2️⃣Search by Nfc id
    @Override
    public WorkerResponseDTO searchByNfcId(String nfcId) {
        Worker worker = workerRepository.findByNfcCardId(nfcId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return workerMapper.toResponseDTO(worker);
    }

    // // 2️⃣2️⃣Updating trust score by Telemetry service
    // @Override
    // public WorkerResponseDTO recalculateTrustScore(long workerId) {
    // Worker worker = workerRepository.findById(workerId)
    // .orElseThrow(() -> new RuntimeException("Worker not found"));

    // int totalDocuments = worker.getDocuments().size();
    // double verifiedDocuments = worker.getDocuments().stream()
    // .filter(j -> j.getDocumentVerificationStatus() == CompletionStatus.COMPLETED)
    // .count();

    // double gigLevel = totalDocuments == 0 ? 1 : (verifiedDocuments /
    // totalDocuments) * 5; // scale 1–5
    // worker.setWorkerLevel(Math.min(Math.max(workerLevel, 1), 5));

    // Worker updated = workerRepository.save(worker);
    // return workerMapper.toResponseDTO(updated);
    // }

    // // 2️⃣3️⃣Updating worker Level by Telemetry service
    // @Override
    // public WorkerResponseDTO recalculateWorkerLevel(long workerId) {
    // Worker worker = workerRepository.findById(workerId)
    // .orElseThrow(() -> new RuntimeException("Worker not found"));

    // double totalJobs = worker.getAssignedJobs().size();
    // double completedJobs = worker.getAssignedJobs().stream()
    // .filter(j -> j.getCompletionStatus() == CompletionStatus.COMPLETED)
    // .count();

    // double gigLevel = totalJobs == 0 ? 1 : (completedJobs / totalJobs) * 5; //
    // scale 1–5
    // worker.setWorkerLevel(Math.min(Math.max(workerLevel, 1), 5));

    // Worker updated = workerRepository.save(worker);
    // return workerMapper.toResponseDTO(updated);
    // }

    // 2️⃣4️⃣Fetching worker applied Jobs for a worker
    // 2️⃣5️⃣Fetching worker applied Jobs hitory of a worker

    // 2️⃣6️⃣Fetching assigned Jobs for a worker
    // 2️⃣7️⃣Fetching assigned Jobs history of a worker

    // 2️⃣8️⃣Fetching location history of a worker

    // 2️⃣9️⃣Fetching documents of a worker
    // 3️⃣0️⃣Fetching documents of multiple workers
}
