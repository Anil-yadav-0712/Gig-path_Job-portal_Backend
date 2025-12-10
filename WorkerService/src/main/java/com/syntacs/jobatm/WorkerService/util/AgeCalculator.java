package com.syntacs.jobatm.WorkerService.util;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

        public static int calculateAge(LocalDate birthDate) {
            if (birthDate == null) {
                return -1;
            }
                return Period.between(birthDate, LocalDate.now()).getYears();
        }
}