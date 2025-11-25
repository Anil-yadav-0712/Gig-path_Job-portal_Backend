package com.syntacs.jobatm.WorkerService.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;
import com.syntacs.jobatm.WorkerService.util.PreferredLanguage;
import com.syntacs.jobatm.WorkerService.util.RegistrationSource;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "workers")
@Data
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workerId;

    // Basic Details
    @NotBlank(message = "Worker name cannot be blank")
    @Size(max = 255)
    private String workerName;

    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;

    @Email(message = "Worker email should be valid")
    @Size(max = 255)
    @Column(nullable = true)
    private String workerEmail;

    @NotBlank(message = "Worker phone cannot be blank")
    @Size(max = 20)
    private String workerPhone;

    @NotBlank(message = "Password hash cannot be blank")
    @Size(max = 100)
    private String workerPasswordHash;

    // Documents
    @NotBlank(message = "Aadhaar number cannot be blank")
    @Size(max = 12)
    @Column(unique = true, nullable = false)
    private String aadhaarNumber;

    @Size(max = 10)
    private String panNumber;

    @Size(max = 20)
    private String voterCardNumber;

    // Registration info
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull(message = "Registration source cannot be null")
    @Enumerated(EnumType.STRING)
    private RegistrationSource createdBy; // SELF or KIOSK

    @Enumerated(EnumType.STRING)
    private RegistrationSource updatedBy;

    private String kioskIdOfRegistration;

    // Verification + NFC Details
    @Column(nullable = false)
    private Boolean isVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NfcCardStatus nfcCardStatus = NfcCardStatus.PENDING;

    @Size(max = 100)
    @Column(unique = true)
    private String nfcCardId;

    // Regional info
    @Enumerated(EnumType.STRING)
    private PreferredLanguage preferredLanguage;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 255)
    private String location;

    @Min(0)
    @Column(nullable = true)
    private Integer preferredTravelDistance;

    // Career Info
    @NotBlank(message = "Work category cannot be blank")
    @Size(max = 100)
    private String workCategory;

    @Column(nullable = false)
    private Double gigLevel = 1.0;

    @Column(nullable = true)
    private Double minWageRange;

    @Column(nullable = true)
    private Double maxWageRange;

    @Column(nullable = false)
    private Double jobAcceptanceRate = 0.0;

    @Column(nullable = false)
    private Double jobCompletionRate = 0.0;


    @PrePersist
    @PreUpdate
    public void validateWageRange() {
        if (minWageRange != null && maxWageRange != null && minWageRange >= maxWageRange) {
            throw new IllegalArgumentException("min_wage_range must be less than max_wage_range");
        }
    }
    
    // public int getAge() {
    //     if (this.birthDate == null) {
    //         return -1;
    //     }
    //     return Period.between(this.birthDate, LocalDate.now()).getYears();
    // }
    public int getAge() {
        if (this.birthDate == null) {
            return -1;
        }
        return Period.between(this.birthDate, LocalDate.now()).getYears();
    }


    // Functions that needs to be overridden 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;

        // If ID is null, objects are not yet persisted → return false
        if (workerId == null || worker.workerId == null) return false;

        return workerId.equals(worker.workerId);
    }

    @Override
    public int hashCode() {
        return workerId != null ? workerId.hashCode() : 0;
    }


    @Override
    public String toString() {
        return "Worker {" +
                "workerId=" + workerId +
                ", workerName='" + workerName + '\'' +
                ", birthDate=" + birthDate +
                ", age=" + getAge() +
                ", workerEmail='" + workerEmail + '\'' +
                ", workerPhone='" + workerPhone + '\'' +
                ", location='" + location + '\'' +
                ", preferredLanguage=" + preferredLanguage +
                ", preferredTravelDistance=" + preferredTravelDistance +
                ", workCategory='" + workCategory + '\'' +
                ", gigLevel=" + gigLevel +
                ", wageRange=(" + minWageRange + " - " + maxWageRange + ")" +
                ", isVerified=" + isVerified +
                ", nfcCardStatus=" + nfcCardStatus +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", kioskIdOfRegistration=" + kioskIdOfRegistration +
                ", jobAcceptanceRate=" + jobAcceptanceRate +
                ", jobCompletionRate=" + jobCompletionRate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    
    //Mappings
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkerSkill> skills = new HashSet<>();
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JsonIgnore
    private Set<WorkerAppliedJobsHistory> appliedJobs = new HashSet<>();

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<WorkerAssignedJobsHistory> assignedJobs = new HashSet<>();
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkerDocument> documents = new HashSet<>();
    
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkerLocationHistory> locationHistory = new HashSet<>();
    
}