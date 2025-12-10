import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@Table(name = "job_required_skills")
@Data
public class JobRequiredSkill {

    @EmbeddedId
    private JobRequiredSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;

    @Column(name = "skill", insertable = false, updatable = false)
    private String skill;

    @Embeddable
    @Data
    @EqualsAndHashCode
    public static class JobRequiredSkillId implements Serializable {

        @Column(name = "job_id")
        private long jobId;

        @Column(name = "skill")
        private String skill;
    }
}