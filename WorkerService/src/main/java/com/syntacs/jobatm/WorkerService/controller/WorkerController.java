package com.syntacs.jobatm.WorkerService.controller;

import com.syntacs.jobatm.WorkerService.dto.LoginRequestDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.service.WorkerService;
import com.syntacs.jobatm.WorkerService.util.InstituteNames;
import com.syntacs.jobatm.WorkerService.util.Languages;
import com.syntacs.jobatm.WorkerService.util.Qualifications;
import com.syntacs.jobatm.WorkerService.util.WorkerCardObject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    // 1️⃣ Register Worker
    @PostMapping("/register")
    public ResponseEntity<WorkerResponseDTO> registerWorker(@RequestBody WorkerRegistrationDTO workerDTO) {
        return ResponseEntity.ok(workerService.registerWorker(workerDTO));
    }

    // 2️⃣ Worker Login
    @PostMapping("/login")
    public ResponseEntity<WorkerResponseDTO> login(@RequestBody LoginRequestDTO request) {
        WorkerResponseDTO response = workerService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    // 3️⃣ Get all Workers List
    @GetMapping
    public ResponseEntity<List<WorkerResponseDTO>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    // 4️⃣ Get a single Worker details by id
    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> getWorker(@PathVariable long id) {
        return ResponseEntity.ok(workerService.getWorker(id));
    }

    // 5️⃣ Search by phone/mobile number
    @GetMapping("/search/phone")
    public ResponseEntity<WorkerResponseDTO> searchByPhone(@RequestParam String phoneNo) {
        return ResponseEntity.ok(workerService.searchByPhone(phoneNo));
    }

    // 6️⃣ Search by email id
    @GetMapping("/search/email")
    public ResponseEntity<WorkerResponseDTO> searchByEmail(@RequestParam String email) {
        return ResponseEntity.ok(workerService.searchByEmail(email));
    }

    // 7️⃣ Update Worker details
    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> updateWorker(@PathVariable long id,
            @RequestBody WorkerRegistrationDTO workerDTO) {
        return ResponseEntity.ok(workerService.updateWorker(id, workerDTO));
    }

    // 8️⃣ Delete Worker entry
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }

    // Below endpoints required for Searching / Filtering by retrieving lots of
    // profiles based on condition

    // 9️⃣ Search by Age Group Range
    @GetMapping("/search/within-age-min-to-max")
    public ResponseEntity<Set<WorkerCardObject>> searchWithinAgeRange(@RequestParam int minAge,
            @RequestParam int maxAge) {
        return ResponseEntity.ok(workerService.searchWithinAgeRange(minAge, maxAge));
    }

    // 🔟 Search by Educational Qualification
    @GetMapping("/search/qualification")
    public ResponseEntity<Set<WorkerCardObject>> searchByQualification(@RequestParam Qualifications qualification) {
        return ResponseEntity.ok(workerService.searchByQualification(qualification));
    }

    // 1️⃣1️⃣Search by Education institute (school / college name)
    @GetMapping("/search/institute")
    public ResponseEntity<Set<WorkerCardObject>> searchByInstituteName(@RequestParam InstituteNames institute) {
        return ResponseEntity.ok(workerService.searchByInstituteName(institute));
    }

    // 1️⃣2️⃣Search by Preferred Language
    @GetMapping("/search/language")
    public ResponseEntity<Set<WorkerCardObject>> searchByLanguage(@RequestParam Languages language) {
        return ResponseEntity.ok(workerService.searchByLanguage(language));
    }

    // 1️⃣3️⃣Search by city/town name
    @GetMapping("/search/city")
    public ResponseEntity<Set<WorkerCardObject>> searchByCity(@RequestParam String cityName) {
        return ResponseEntity.ok(workerService.searchByCity(cityName));
    }

    // 1️⃣4️⃣Search by district name
    @GetMapping("/search/district")
    public ResponseEntity<Set<WorkerCardObject>> searchByDistrict(@RequestParam String districtName) {
        return ResponseEntity.ok(workerService.searchByDistrict(districtName));
    }

    // 1️5️⃣Search by state name
    @GetMapping("/search/state")
    public ResponseEntity<Set<WorkerCardObject>> searchByState(@RequestParam String stateName) {
        return ResponseEntity.ok(workerService.searchByState(stateName));
    }

    // 1️⃣6️⃣Search by country name
    @GetMapping("/search/country")
    public ResponseEntity<Set<WorkerCardObject>> searchByCountry(@RequestParam String countryName) {
        return ResponseEntity.ok(workerService.searchByCountry(countryName));
    }

    // 1️⃣7️⃣Search by Kiosk id of Registration
    @GetMapping("/search/kiosk-id")
    public ResponseEntity<Set<WorkerCardObject>> searchByKioskIdOfRegistration(@RequestParam Long kioskId) {
        return ResponseEntity.ok(workerService.searchByKioskIdOfRegistration(kioskId));
    }

    // 1️⃣8️⃣Search by Trust score
    @GetMapping("/search/trust-score")
    public ResponseEntity<Set<WorkerCardObject>> searchByTrustScore(@RequestParam int trustScore) {
        return ResponseEntity.ok(workerService.searchByTrustScore(trustScore));
    }

    // 1️⃣9️⃣Search by worker Level
    @GetMapping("/search/worker-level")
    public ResponseEntity<Set<WorkerCardObject>> searchByWorkerLevel(@RequestParam int workerLevel) {
        return ResponseEntity.ok(workerService.searchByWorkerLevel(workerLevel));
    }

    // These below endpoints are meant for Verification Admin only
    // 2️⃣0️⃣ Verification status set to true by admin - when all documents are
    // verified
    @PatchMapping("/verify/{id}")
    public ResponseEntity<WorkerResponseDTO> verifyWorker(@PathVariable long id) {
        // Example: Notify another MS after verification
        // RestTemplate.patchForObject("http://localhost:8086/api/notifications/worker/verified",
        // verifiedWorker, Void.class);
        return ResponseEntity.ok(workerService.verifyWorkerByAdmin(id));
    }

    // 2️⃣1️⃣Issue of a nfc id - (currently an unique qr code)
    @PatchMapping("/issue-nfc/{id}")
    public ResponseEntity<WorkerResponseDTO> issueNfc(@PathVariable long id) {
        return ResponseEntity.ok(workerService.issueNfcCard(id));
    }

    // 2️⃣2️⃣Search by Nfc id
    @GetMapping("/search/nfc/{nfc-id}")
    public ResponseEntity<WorkerResponseDTO> searchByNfcId(@PathVariable String nfcId) {
        return ResponseEntity.ok(workerService.searchByNfcId(nfcId));
    }

    // These below endpoints will be accessed by Telemetry & Cronjob type services
    // // 2️⃣2️⃣Updating trust score by Telemetry service
    // @PatchMapping("/recalculate-trustscore/{id}")
    // public ResponseEntity<WorkerResponseDTO> recalculateTrustScore(@PathVariable
    // long id,
    // @PathVariable int updated_trust_score) {
    // return ResponseEntity.ok(workerService.recalculateTrustScore(id,
    // updated_trust_score));
    // }

    // // 2️⃣3️⃣Updating worker Level by Telemetry service
    // @PatchMapping("/recalculate-level/{id}")
    // public ResponseEntity<WorkerResponseDTO> recalculateWorkerLevel(@PathVariable
    // long id,
    // @PathVariable int updated_worker_level) {
    // return ResponseEntity.ok(workerService.recalculateWorkerLevel(id,
    // updated_worker_level));
    // }

    // 2️⃣4️⃣Fetching worker applied Jobs for a worker
    // 2️⃣5️⃣Fetching worker applied Jobs hitory of a worker

    // 2️⃣6️⃣Fetching assigned Jobs for a worker
    // 2️⃣7️⃣Fetching assigned Jobs history of a worker

    // 2️⃣8️⃣Fetching location history of a worker

    // 2️⃣9️⃣Fetching documents of a worker
    // 3️⃣0️⃣Fetching documents of multiple workers
}