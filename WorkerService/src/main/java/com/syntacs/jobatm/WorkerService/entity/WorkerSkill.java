package com.syntacs.jobatm.WorkerService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "worker_skills",
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"worker_id", "skill"})
       })
public class WorkerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;     // <-- SINGLE PRIMARY KEY

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String skill;

    //Constructors
    public WorkerSkill() {}

    public WorkerSkill(Worker worker, String skill) {
        this.worker = worker;
        this.skill = skill;
    }


    //Functions to needs to be overrridden
     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkerSkill)) return false;
        WorkerSkill that = (WorkerSkill) o;

        // If id is null, object is transient → not equal to saved objects
        if (this.id == null || that.id == null) return false;

        return this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return (id != null) ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkerSkill {" +
                "id=" + id +
                ", workerId=" + (worker != null ? worker.getWorkerId() : null) +
                ", skill='" + skill + '\'' +
                '}';
    }
}
