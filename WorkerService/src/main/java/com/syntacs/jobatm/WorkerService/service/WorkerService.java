package com.syntacs.jobatm.WorkerService.service;

import java.util.List;
import java.util.Set;

import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.util.InstituteNames;
import com.syntacs.jobatm.WorkerService.util.Languages;
import com.syntacs.jobatm.WorkerService.util.Qualifications;
import com.syntacs.jobatm.WorkerService.util.WorkerCardObject;

public interface WorkerService {

    WorkerResponseDTO registerWorker(WorkerRegistrationDTO workerInputData);

    WorkerResponseDTO login(String username, String password);

    WorkerResponseDTO getWorker(long workerId);

    WorkerResponseDTO searchByPhone(String phoneNo);

    WorkerResponseDTO searchByEmail(String email);

    List<WorkerResponseDTO> getAllWorkers();

    WorkerResponseDTO updateWorker(long workerId, WorkerRegistrationDTO workerUpdatedData);

    void deleteWorker(long workerId);

    Set<WorkerCardObject> searchWithinAgeRange(int minAge, int maxAge);

    // Set<WorkerCardObject> searchByQualification(Qualifications qualification);

    // Set<WorkerCardObject> searchByInstituteName(InstituteNames institute);

    // Set<WorkerCardObject> searchByLanguage(Languages language);

    Set<WorkerCardObject> searchByCity(String cityName);

    Set<WorkerCardObject> searchByDistrict(String districtName);

    Set<WorkerCardObject> searchByState(String stateName);

    Set<WorkerCardObject> searchByCountry(String countryName);

    Set<WorkerCardObject> searchByKioskIdOfRegistration(Long kioskId);

    // Set<WorkerCardObject> searchByTrustScore(int trustScore);

    // Set<WorkerCardObject> searchByWorkerLevel(int workerLevel);

    // WorkerResponseDTO verifyWorkerByAdmin(long workerId, boolean
    // verificationTrue);

    // WorkerResponseDTO issueNfcCard(long workerId, String nfc_id_generated);

    // WorkerResponseDTO recalculateTrustScore(long workerId, int
    // updated_trust_score);

    // WorkerResponseDTO recalculateWorkerLevel(long workerId, int
    // updated_worker_level);

    Set<WorkerCardObject> searchByQualification(Object qualification);

    Set<WorkerCardObject> searchByInstituteName(Object institute);

    Set<WorkerCardObject> searchByLanguage(Object language);

    // WorkerResponseDTO verifyWorkerByAdmin(long workerId);

    // WorkerResponseDTO recalculateWorkerLevel(long workerId);

    // WorkerResponseDTO recalculateTrustScore(long workerId);

    WorkerResponseDTO issueNfcCard(long workerId);

    WorkerResponseDTO verifyWorkerByAdmin(long workerId);

    Set<WorkerCardObject> searchByTrustScore(int trustScore);

    Set<WorkerCardObject> searchByWorkerLevel(int workerLevel);

    WorkerResponseDTO searchByNfcId(String nfcId);
}
