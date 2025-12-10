package com.syntacs.jobatm.WorkerService.entity;

import com.syntacs.jobatm.WorkerService.util.Skill;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "worker_skills", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "worker_id", "skill" })
})
public class WorkerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId; // <-- SINGLE PRIMARY KEY

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String skill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    // Constructors
    public WorkerSkill() {
    }

    public WorkerSkill(Worker worker, String skillEnum) {
        this.worker = worker;
        this.skill = skillEnum;
    }

    // Functions to needs to be overrridden
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof WorkerSkill))
            return false;
        WorkerSkill that = (WorkerSkill) o;

        // If id is null, object is transient → not equal to saved objects
        if (this.skillId == null || that.skillId == null)
            return false;

        return this.skillId.equals(that.skillId);
    }

    @Override
    public int hashCode() {
        return (skillId != null) ? skillId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerSkill {" +
                "Skill id = " + skillId +
                ", Skill name = '" + skill + '\'' +
                ", Worker id = " + (worker != null ? worker.getWorkerId() : null) +
                '}';
    }
}