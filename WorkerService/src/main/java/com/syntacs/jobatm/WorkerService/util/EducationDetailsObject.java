package com.syntacs.jobatm.WorkerService.util;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Embeddable
public class EducationDetailsObject {
    // private Qualifications qualification;
    // private InstituteNames instituteName;
    // private GovtAuthorityList issuingAuthority;

    @NotBlank
    private String qualification;

    @Nullable
    private String instituteName;

    @NotBlank
    private String issuingAuthority;
    @NotBlank
    private String idNumber;

    @NotNull
    private Integer marksObtained;
    @NotNull
    private Integer totalMarks;

    private Float percentage;

    // private Document documentProof;
    @Nullable
    private Boolean isVerified;

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
        recalculatePercentage();
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
        recalculatePercentage();
    }

    private void recalculatePercentage() {
        if (marksObtained != null && totalMarks != null && totalMarks != 0) {
            this.percentage = (float) marksObtained / totalMarks * 100;
        }
    }

}