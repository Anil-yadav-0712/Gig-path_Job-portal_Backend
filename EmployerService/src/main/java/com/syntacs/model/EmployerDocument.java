import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import java.sql.Timestamp;

@Entity
@Table(name = "employer_documents")
public class EmployerDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @NotBlank(message = "Document type cannot be blank")
    @Size(max = 50)
    private String documentType;

    @Size(max = 100)
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING'")
    private DocumentVerificationStatus verificationStatus = DocumentVerificationStatus.PENDING;

    private long verifiedBy;

    private Timestamp verifiedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAtTime;

    // Getters and Setters (omitted for brevity)
}