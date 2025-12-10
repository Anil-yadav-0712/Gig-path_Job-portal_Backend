import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employers")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employerId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 20)
    private String phone;

    @NotBlank(message = "Password hash cannot be blank")
    @Size(max = 60)
    private String passwordHash;

    @NotNull(message = "Type cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('INDIVIDUAL', 'BUSINESS')")
    private EmployerType type;

    @Size(max = 12)
    private String aadhaarNumber;

    @Size(max = 10)
    private String panNumber;

    @Size(max = 15)
    private String gstin;

    @Size(max = 21)
    private String cin;

    @Size(max = 255)
    private String businessName;

    @NotBlank(message = "Location cannot be blank")
    @Size(max = 255)
    private String location;

    private Boolean isVerified = false;

    private double rating = 0.0;

    @NotNull(message = "Registration source cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationSource registrationSource;

    @Size(max = 50)
    private String createdBy;

    @Size(max = 50)
    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAtTime;

    @UpdateTimestamp
    private Timestamp updatedAtTime;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployerDocument> documents = new HashSet<>();

    // Getters and Setters (omitted for brevity)
}