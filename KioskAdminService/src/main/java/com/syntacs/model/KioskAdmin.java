import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "kiosk_admins")
public class KioskAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    @Column(nullable = false)
    private String phone;

    @NotBlank(message = "Password hash cannot be blank")
    @Size(max = 60, message = "Password hash must not exceed 60 characters")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "Centre name cannot be blank")
    @Size(max = 255, message = "Centre name must not exceed 255 characters")
    @Column(nullable = false)
    private String centreName;

    @NotBlank(message = "Centre region cannot be blank")
    @Size(max = 255, message = "Centre region must not exceed 255 characters")
    @Column(nullable = false)
    private String centreRegion;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
    private KioskAdminStatus status = KioskAdminStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAtTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAtTime;

    // Getters and Setters (omitted for brevity)
}