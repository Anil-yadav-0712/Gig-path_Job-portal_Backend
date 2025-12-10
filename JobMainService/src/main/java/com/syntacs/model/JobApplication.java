import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "job_applications", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "job_id", "worker_id" })
})
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "worker_id", nullable = false)
    private long workerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobApplicationStatus status;

    @CreationTimestamp
    @Column(name = "applied_at", updatable = false)
    private Timestamp appliedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAtTime;
}

// enum JobApplicationStatus {
// PENDING, SHORTLISTED, APPROVED, REJECTED, COMPLETED, CANCELLED
// }