package com.syntacs.jobatm.WorkerService.controller;

import com.syntacs.jobatm.WorkerService.dto.LoginRequestDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerRegistrationDTO;
import com.syntacs.jobatm.WorkerService.dto.WorkerResponseDTO;
import com.syntacs.jobatm.WorkerService.repository.WorkerRepository;
import com.syntacs.jobatm.WorkerService.service.WorkerService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/register")
    public ResponseEntity<WorkerResponseDTO> registerWorker(@RequestBody WorkerRegistrationDTO workerDTO) {
        return ResponseEntity.ok(workerService.registerWorker(workerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<WorkerResponseDTO> login(@RequestBody LoginRequestDTO request) {
        WorkerResponseDTO response = workerService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> getWorker(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorker(id));
    }

    @GetMapping
    public ResponseEntity<List<WorkerResponseDTO>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkerResponseDTO> updateWorker(@PathVariable Long id,
            @RequestBody WorkerRegistrationDTO workerDTO) {
        return ResponseEntity.ok(workerService.updateWorker(id, workerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify/{id}")
    public ResponseEntity<WorkerResponseDTO> verifyWorker(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.verifyWorkerByAdmin(id));
    }

    @PostMapping("/issue-nfc/{id}")
    public ResponseEntity<WorkerResponseDTO> issueNfc(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.issueNfcCard(id));
    }

    @PostMapping("/recalculate-gig/{id}")
    public ResponseEntity<WorkerResponseDTO> recalculateGig(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.recalculateGigLevel(id));
    }
}

// package com.jobatm.worker.controller;

// import com.jobatm.worker.dto.WorkerDTO;
// import com.jobatm.worker.service.WorkerService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;

// import java.util.List;

// @RestController
// @RequestMapping("/api/workers")
// public class WorkerController {

// private final WorkerService workerService;
// private final RestTemplate restTemplate;

// public WorkerController(WorkerService workerService, RestTemplate
// restTemplate) {
// this.workerService = workerService;
// this.restTemplate = restTemplate;
// }

// // 1️⃣ Register worker
// @PostMapping("/register")
// public ResponseEntity<WorkerDTO> registerWorker(@RequestBody WorkerDTO
// workerDTO) {
// WorkerDTO createdWorker = workerService.registerWorker(workerDTO);
// return ResponseEntity.ok(createdWorker);
// }

// // 2️⃣ Get worker by ID
// @GetMapping("/{id}")
// public ResponseEntity<WorkerDTO> getWorkerById(@PathVariable Long id) {
// WorkerDTO worker = workerService.getWorkerById(id);
// return ResponseEntity.ok(worker);
// }

// // 3️⃣ Get all workers
// @GetMapping
// public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
// return ResponseEntity.ok(workerService.getAllWorkers());
// }

// // 4️⃣ Update worker
// @PutMapping("/{id}")
// public ResponseEntity<WorkerDTO> updateWorker(@PathVariable Long id,
// @RequestBody WorkerDTO workerDTO) {
// WorkerDTO updated = workerService.updateWorker(id, workerDTO);
// return ResponseEntity.ok(updated);
// }

// // 5️⃣ Delete worker
// @DeleteMapping("/{id}")
// public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
// workerService.deleteWorker(id);
// return ResponseEntity.noContent().build();
// }

// // 6️⃣ Admin verification
// @PostMapping("/{id}/verify")
// public ResponseEntity<WorkerDTO> verifyWorker(@PathVariable Long id) {
// WorkerDTO verifiedWorker = workerService.verifyWorkerByAdmin(id);

// // Example: Notify another MS after verification
// // restTemplate.postForObject("http://other-ms/worker/verified",
// verifiedWorker, Void.class);

// return ResponseEntity.ok(verifiedWorker);
// }

// // 7️⃣ Issue NFC card
// @PostMapping("/{id}/nfc/issue")
// public ResponseEntity<WorkerDTO> issueNfcCard(@PathVariable Long id) {
// WorkerDTO workerWithNfc = workerService.issueNfcCard(id);

// // Example: Notify NFC MS for activation
// // restTemplate.postForObject("http://nfc-ms/nfc/activate", workerWithNfc,
// Void.class);

// return ResponseEntity.ok(workerWithNfc);
// }

// // 8️⃣ Recalculate gig level
// @PostMapping("/{id}/giglevel/recalculate")
// public ResponseEntity<WorkerDTO> recalculateGigLevel(@PathVariable Long id) {
// WorkerDTO updated = workerService.recalculateGigLevel(id);
// return ResponseEntity.ok(updated);
// }

// // 9️⃣ Search by location
// @GetMapping("/search/location")
// public ResponseEntity<List<WorkerDTO>> searchByLocation(@RequestParam String
// location) {
// return ResponseEntity.ok(workerService.searchByLocation(location));
// }

// // 🔟 Search by phone
// @GetMapping("/search/phone")
// public ResponseEntity<WorkerDTO> searchByPhone(@RequestParam String phone) {
// return ResponseEntity.ok(workerService.searchByPhone(phone));
// }

// // 1️⃣1️⃣ Search by gig level
// @GetMapping("/search/giglevel")
// public ResponseEntity<List<WorkerDTO>> searchByGigLevel(@RequestParam Double
// gigLevel) {
// return ResponseEntity.ok(workerService.searchByGigLevel(gigLevel));
// }

// // 1️⃣2️⃣ Search by registration source (SELF/KIOSK)
// @GetMapping("/search/registrationSource")
// public ResponseEntity<List<WorkerDTO>>
// searchByRegistrationSource(@RequestParam String source) {
// return ResponseEntity.ok(workerService.searchByRegistrationSource(source));
// }
// }
