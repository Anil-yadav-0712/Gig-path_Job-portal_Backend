package com.syntacs.jobatm.WorkerService.util;

import java.util.Set;

import lombok.Data;

@Data
public class WorkerCardObject {

    public String profilePhoto; // Base 64 encoded code
    public String workerName;
    public int Age;
    public String city;
    public Languages language;
    public boolean isVerified;
    public String workCategory;
    public Set<Skill> skills;
    public int trustScore;
    public int workerLevel;
}
