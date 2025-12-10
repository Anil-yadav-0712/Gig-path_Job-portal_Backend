import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private long jobId;

    @Column(name = "employer_id", nullable = false)
    private long employerId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "work_category", nullable = false)
    private String workCategory;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "min_budget", nullable = false)
    private double minBudget;

    @Column(name = "max_budget", nullable = false)
    private double maxBudget;

    @Column(name = "required_no_of_workers", nullable = false)
    private integer requiredNoOfWorkers;

    @Column(name = "min_gig_level")
    private double minGigLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAtTime;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAtTime;

    @Column(name = "completed_at")
    private Timestamp completedAt;

    @Column(name = "estimated_duration")
    private integer estimatedDuration;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobRequiredSkill> requiredSkills;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JobApplication> applications;
}

// enum JobStatus {
// OPEN, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
// }