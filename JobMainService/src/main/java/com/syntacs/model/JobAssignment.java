import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "job_assignments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"job_id", "worker_id"})
})
@Data
public class JobAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private long assignmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @Column(name = "worker_id", nullable = false)
    private long workerId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private JobApplication application;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private JobAssignmentStatus status;

    @CreationTimestamp
    @Column(name = "assigned_at", updatable = false)
    private Timestamp assignedAt;

    @Column(name = "completed_at")
    private Timestamp completedAt;
}

// enum JobAssignmentStatus {
//     ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
// }