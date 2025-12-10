package com.syntacs.jobatm.WorkerService.entity;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

import com.syntacs.jobatm.WorkerService.util.AddressDetailsObject;
import com.syntacs.jobatm.WorkerService.util.EducationDetailsObject;
import com.syntacs.jobatm.WorkerService.util.NfcCardStatus;
import com.syntacs.jobatm.WorkerService.util.Languages;
import com.syntacs.jobatm.WorkerService.util.RegistrationType;

@Entity
@Table(name = "workers")
@Getter
@Setter
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

    @Lob
    @Column(name = "file_data", nullable = true, columnDefinition = "LONGBLOB")
    private byte[] profilePhoto;

    // Communication details
    @NotBlank(message = "Worker phone cannot be blank")
    @Size(max = 20)
    private String workerPhone;

    @Email(message = "Worker email should be valid")
    @Size(max = 255)
    @Column(nullable = true)
    private String workerEmail;

    @NotBlank(message = "Password hash cannot be blank")
    @Size(max = 100)
    private String workerPasswordHash;

    // Regional Information
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNo", column = @Column(name = "worker_address_house_no")),
            @AttributeOverride(name = "streetNo", column = @Column(name = "worker_address_street_no")),
            @AttributeOverride(name = "landmark", column = @Column(name = "worker_address_landmark")),
            @AttributeOverride(name = "city", column = @Column(name = "worker_address_city")),
            @AttributeOverride(name = "district", column = @Column(name = "worker_address_district")),
            @AttributeOverride(name = "state", column = @Column(name = "worker_address_state")),
            @AttributeOverride(name = "country", column = @Column(name = "worker_address_country")),
            @AttributeOverride(name = "pincode", column = @Column(name = "worker_address_pincode"))
    })
    private AddressDetailsObject workerAddress;

    // Location
    @Column(nullable = true)
    private Double latitude;
    @Column(nullable = true)
    private Double longitude;

    @Min(0)
    private Integer preferredTravelDistance; // was int → changed to Integer

    @Enumerated(EnumType.STRING)
    private Languages preferredLanguage;

    // Educational details
    @ElementCollection
    @CollectionTable(name = "worker_education_details", joinColumns = @JoinColumn(name = "worker_id"))
    private Set<EducationDetailsObject> workerEducationDetails = new HashSet<>();

    // Documents
    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkerDocument> documents = new HashSet<>();

    // Verification + NFC Details
    @Column(nullable = false)
    private Boolean isVerified = false; // was boolean → changed to Boolean

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private NfcCardStatus nfcCardStatus;

    @Size(max = 100)
    @Column(unique = true, nullable = true)
    private String nfcCardId;

    // Career Info
    @NotBlank(message = "Work category cannot be blank")
    @Size(max = 25)
    private String workCategory;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkerSkill> skills = new HashSet<>();

    // ⚠ Changed all wage + rate fields to Double
    @Column(nullable = true)
    private Double minWageRange = 0.0;
    @Column(nullable = true)
    private Double maxWageRange = 0.0;

    @Column(nullable = true)
    private Double workAcceptanceRate = null;

    @Column(nullable = true)
    private Double workCompletionRate = null;

    @Column(nullable = false)
    private Integer workerTrustScore = 0;
    @Column(nullable = false)
    private Integer workerLevel = 0;

    @CreationTimestamp
    private LocalDateTime createdAtTime;

    @UpdateTimestamp
    private LocalDateTime updatedAtTime;

    @Column(nullable = true)
    private LocalDateTime deletedAtTime;

    @NotNull(message = "Registration source cannot be null")
    @Enumerated(EnumType.STRING)
    private RegistrationType createdBy;

    @Column(nullable = true)
    private String kioskIdOfRegistration;

    @Enumerated(EnumType.STRING)
    private RegistrationType updatedBy;

    @Column(nullable = true)
    private String kioskIdOfUpdation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private RegistrationType deletedBy;

    @Column(nullable = true)
    private String kioskIdOfDeletion;

    // @PrePersist
    // @PreUpdate
    // public void validateWageRange() {
    // if (minWageRange > 0 && maxWageRange > 0 && minWageRange >= maxWageRange) {
    // throw new IllegalArgumentException("min_wage_range must be less than
    // max_wage_range");
    // }
    // }

    // Functions that needs to be overridden
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Worker worker = (Worker) o;

        // If ID is null, objects are not yet persisted → return false
        if (workerId == null || worker.workerId == null)
            return false;

        return workerId.equals(worker.workerId);
    }

    @Override
    public int hashCode() {
        return workerId != null ? workerId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Worker {" +
                "id = " + workerId +
                ", Name = '" + workerName + '\'' +
                ", Birth date = " + birthDate +
                // ", Age (years) = " + getAge() +
                ", Mobile number = '" + workerPhone + '\'' +
                ", Email = '" + workerEmail + '\'' +
                ", Address details = '" + workerAddress + '\'' +
                ", Location cordinates = (" + latitude + ", " + longitude + ")" +
                ", Preferred travel distance = " + preferredTravelDistance +
                ", Preferred language = " + preferredLanguage +
                ", Educational details = " + workerEducationDetails +
                ", Uploaded identity proofs = " + documents +
                ", isVerified = " + isVerified +
                ", NFC card status = " + nfcCardStatus +
                ", NFC card id = '" + nfcCardId + '\'' +
                ", Work category = '" + workCategory + '\'' +
                ", Wage range = (" + minWageRange + " - " + maxWageRange + ")" +
                ", Job acceptance rate = " + workAcceptanceRate +
                ", Job completion rate = " + workCompletionRate +
                ", Trust score = " + workerTrustScore +
                ", Worker level = " + workerLevel +
                ", Created at time = " + createdAtTime +
                ", Updated at time = " + updatedAtTime +
                ", Created by = " + createdBy +
                ", Kiosk id of Registration = " + kioskIdOfRegistration +
                ", Updated by = " + updatedBy +
                ", Kiosk id of updation = " + kioskIdOfUpdation +
                '}';
    }

    // Mappings

    // @OneToMany(mappedBy = "worker", cascade = CascadeType.PERSIST, orphanRemoval
    // = false)
    // @JsonIgnore
    // private Set<WorkerAppliedJobsHistory> appliedJobs = new HashSet<>();

    // @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL)
    // @JsonIgnore
    // private Set<WorkerAssignedJobsHistory> assignedJobs = new HashSet<>();

    // @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval =
    // true)
    // @JsonIgnore
    // private Set<WorkerLocationHistory> locationHistory = new HashSet<>();

}